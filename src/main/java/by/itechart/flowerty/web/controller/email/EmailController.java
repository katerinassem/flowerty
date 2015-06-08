/**
 * @author Eugene Putsykovich(slesh) Apr 1	4, 2015
 *
 */
package by.itechart.flowerty.web.controller.email;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javassist.tools.web.BadHttpRequest;

import javax.mail.MessagingException;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import by.itechart.flowerty.jms.core.FlowertyMessagePublisher;
import by.itechart.flowerty.jms.mail.MailService;
import by.itechart.flowerty.local.settings.Settings;
import by.itechart.flowerty.persistence.model.Contact;
import by.itechart.flowerty.persistence.model.Phone;
import by.itechart.flowerty.security.service.UserDetailsServiceImpl;
import by.itechart.flowerty.web.controller.util.FlowertUtil;


/**
 * @author Eugene Putsykovich(slesh) Apr 14, 2015
 *         execute send mail for specific contacts
 */
@Controller
public class EmailController {

        private Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

        @Autowired
        private Settings settings;

        @Autowired
        private MailService mailService;

        @Autowired
        private FlowertyMessagePublisher publisher;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @ResponseBody
        @RequestMapping(value = "email/templates", method = RequestMethod.GET)
        public FlowertTemplate[] emailTemplate() throws JsonGenerationException, JsonMappingException, FileNotFoundException, IOException {

                return getTemplates();
        }

        @ResponseBody
        @RequestMapping(value = "email/send", method = RequestMethod.POST)
        public void sendEmail(@RequestParam("email") String emailJson,
                              @RequestParam("template") String templateJson,
                              @RequestPart(value = "file", required = false) MultipartFile[] attachments) throws BadHttpRequest {

                LOGGER.info("send email: {}. number of attachments: {}", emailJson, attachments == null ? 0 : attachments.length);
                try {
                        ObjectMapper objectMapper = new ObjectMapper();

                        EmailInfo email = objectMapper.readValue(emailJson, EmailInfo.class);
                        FlowertTemplate template = objectMapper.readValue(templateJson, FlowertTemplate.class);
                        Contact sender = userDetailsService.getCurrentContact();

                        for (Contact to : email.getTo()) {
                                email.setText(buildMessageBody(template, to.getName(), sender));
                                if (attachments == null || attachments.length == 0) {
                                        publisher.send(to.getEmail(), email.getSubject(), email.getText());
                                } else {
                                        publisher.send(to.getEmail(), email.getSubject(), email.getText(), convert(attachments));
                                        FlowertUtil.saveMultiparts(settings.getAttachmentsPath(), attachments);
                                }
                        }
                } catch (IOException | MessagingException e) {
                        LOGGER.error(e.getMessage());

                        throw new BadHttpRequest(e);
                }
        }

        private Map<String, byte[]> convert(MultipartFile[] attachments) throws IOException {

                Map<String, byte[]> resources = new HashMap<String, byte[]>(attachments.length);
                for (MultipartFile attachment : attachments) {
                        String name = attachment.getOriginalFilename();
                        byte[] value = IOUtils.toByteArray(attachment.getInputStream());
                        resources.put(name, value);
                }

                return resources;
        }

        private FlowertTemplate[] getTemplates() throws FileNotFoundException, IOException {

                File[] files = new File(getClass().getClassLoader().getResource("templates").getPath()).listFiles();
                FlowertTemplate[] templates = new FlowertTemplate[files.length];
                for (int i = 0; i < files.length; ++i) {
                        String name = files[i].getName();
                        name = name.substring(0, name.indexOf(".st"));
                        String value = IOUtils.toString(new FileInputStream(files[i]));
                        templates[i] = new FlowertTemplate(name, value);
                }

                return templates;
        }

        private String buildMessageBody(FlowertTemplate template, String toName, Contact sender) {

                StringTemplate st = new StringTemplate(template.getValue());

                st.setAttribute("NAME", toName);

                String fullname = String.format("%s %s %s", sender.getName(), sender.getSurname(), sender.getFathername());
                st.setAttribute("US_FULL_NAME", fullname);

                if (sender.getPhones() != null && sender.getPhones().size() > 0) {
                        Phone phone = sender.getPhones().iterator().next();

                        String usPhone = String.format("+%d %d, %d", phone.getCountry(), phone.getOperator(), phone.getNumber());
                        st.setAttribute("US_PHONE", usPhone);
                }

                st.setAttribute("US_EMAIL", sender.getEmail());

                return st.toString();
        }
}
