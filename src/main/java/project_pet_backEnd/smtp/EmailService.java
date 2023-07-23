package project_pet_backEnd.smtp;


import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import project_pet_backEnd.smtp.dto.EmailResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Data
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    //普通的寄信
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    //格式為html 渲染
    public  void sendEmail(EmailResponse emailResponse){
        MimeMessage msg =mailSender.createMimeMessage();
        MimeMessageHelper helper =null;
        try {
            helper = new MimeMessageHelper(msg, true, "UTF-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            helper.setTo(emailResponse.getTo());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            helper.setSubject(emailResponse.getSubject());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            helper.setText(getHtmlBody(emailResponse),true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(msg);
    }

    public  String getHtmlBody(EmailResponse emailResponse){
        String html = "<!DOCTYPE html\n" +
        "    PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                "    style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n" +
                "\n" +
                "<head>\n" +
                "    <link type=\"text/css\" id=\"dark-mode\" rel=\"stylesheet\" href>\n" +
                "    <link type=\"text/css\" id=\"dark-mode\" rel=\"stylesheet\" href>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <title>New email template 2023-06-27</title><!--[if (mso 16)]>\n" +
                "<style type=\"text/css\">\n" +
                "a {text-decoration: none;}\n" +
                "</style>\n" +
                "<![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "<o:OfficeDocumentSettings>\n" +
                "<o:AllowPNG></o:AllowPNG>\n" +
                "<o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "</o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "    <style type=\"text/css\">\n" +
                "        #outlook a {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "            line-height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .es-button {\n" +
                "            mso-style-priority: 100 !important;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "        a[x-apple-data-detectors] {\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "            font-size: inherit !important;\n" +
                "            font-family: inherit !important;\n" +
                "            font-weight: inherit !important;\n" +
                "            line-height: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        .es-desk-hidden {\n" +
                "            display: none;\n" +
                "            float: left;\n" +
                "            overflow: hidden;\n" +
                "            width: 0;\n" +
                "            max-height: 0;\n" +
                "            line-height: 0;\n" +
                "            mso-hide: all;\n" +
                "        }\n" +
                "\n" +
                "        @media only screen and (max-width:600px) {\n" +
                "\n" +
                "            p,\n" +
                "            ul li,\n" +
                "            ol li,\n" +
                "            a {\n" +
                "                line-height: 150% !important\n" +
                "            }\n" +
                "\n" +
                "            h1,\n" +
                "            h2,\n" +
                "            h3,\n" +
                "            h1 a,\n" +
                "            h2 a,\n" +
                "            h3 a {\n" +
                "                line-height: 120% !important\n" +
                "            }\n" +
                "\n" +
                "            h1 {\n" +
                "                font-size: 30px !important;\n" +
                "                text-align: center\n" +
                "            }\n" +
                "\n" +
                "            h2 {\n" +
                "                font-size: 26px !important;\n" +
                "                text-align: center\n" +
                "            }\n" +
                "\n" +
                "            h3 {\n" +
                "                font-size: 20px !important;\n" +
                "                text-align: center\n" +
                "            }\n" +
                "\n" +
                "            .es-header-body h1 a,\n" +
                "            .es-content-body h1 a,\n" +
                "            .es-footer-body h1 a {\n" +
                "                font-size: 30px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-header-body h2 a,\n" +
                "            .es-content-body h2 a,\n" +
                "            .es-footer-body h2 a {\n" +
                "                font-size: 26px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-header-body h3 a,\n" +
                "            .es-content-body h3 a,\n" +
                "            .es-footer-body h3 a {\n" +
                "                font-size: 20px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-header-body p,\n" +
                "            .es-header-body ul li,\n" +
                "            .es-header-body ol li,\n" +
                "            .es-header-body a {\n" +
                "                font-size: 16px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-content-body p,\n" +
                "            .es-content-body ul li,\n" +
                "            .es-content-body ol li,\n" +
                "            .es-content-body a {\n" +
                "                font-size: 16px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-footer-body p,\n" +
                "            .es-footer-body ul li,\n" +
                "            .es-footer-body ol li,\n" +
                "            .es-footer-body a {\n" +
                "                font-size: 16px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-infoblock p,\n" +
                "            .es-infoblock ul li,\n" +
                "            .es-infoblock ol li,\n" +
                "            .es-infoblock a {\n" +
                "                font-size: 12px !important\n" +
                "            }\n" +
                "\n" +
                "            *[class=\"gmail-fix\"] {\n" +
                "                display: none !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-txt-c,\n" +
                "            .es-m-txt-c h1,\n" +
                "            .es-m-txt-c h2,\n" +
                "            .es-m-txt-c h3 {\n" +
                "                text-align: center !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-txt-r,\n" +
                "            .es-m-txt-r h1,\n" +
                "            .es-m-txt-r h2,\n" +
                "            .es-m-txt-r h3 {\n" +
                "                text-align: right !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-txt-l,\n" +
                "            .es-m-txt-l h1,\n" +
                "            .es-m-txt-l h2,\n" +
                "            .es-m-txt-l h3 {\n" +
                "                text-align: left !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-txt-r img,\n" +
                "            .es-m-txt-c img,\n" +
                "            .es-m-txt-l img {\n" +
                "                display: inline !important\n" +
                "            }\n" +
                "\n" +
                "            .es-button-border {\n" +
                "                display: block !important\n" +
                "            }\n" +
                "\n" +
                "            a.es-button,\n" +
                "            button.es-button {\n" +
                "                font-size: 20px !important;\n" +
                "                display: block !important;\n" +
                "                border-left-width: 0px !important;\n" +
                "                border-right-width: 0px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-btn-fw {\n" +
                "                border-width: 10px 0px !important;\n" +
                "                text-align: center !important\n" +
                "            }\n" +
                "\n" +
                "            .es-adaptive table,\n" +
                "            .es-btn-fw,\n" +
                "            .es-btn-fw-brdr,\n" +
                "            .es-left,\n" +
                "            .es-right {\n" +
                "                width: 100% !important\n" +
                "            }\n" +
                "\n" +
                "            .es-content table,\n" +
                "            .es-header table,\n" +
                "            .es-footer table,\n" +
                "            .es-content,\n" +
                "            .es-footer,\n" +
                "            .es-header {\n" +
                "                width: 100% !important;\n" +
                "                max-width: 600px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-adapt-td {\n" +
                "                display: block !important;\n" +
                "                width: 100% !important\n" +
                "            }\n" +
                "\n" +
                "            .adapt-img {\n" +
                "                width: 100% !important;\n" +
                "                height: auto !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p0 {\n" +
                "                padding: 0px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p0r {\n" +
                "                padding-right: 0px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p0l {\n" +
                "                padding-left: 0px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p0t {\n" +
                "                padding-top: 0px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p0b {\n" +
                "                padding-bottom: 0 !important\n" +
                "            }\n" +
                "\n" +
                "            .es-m-p20b {\n" +
                "                padding-bottom: 20px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-mobile-hidden,\n" +
                "            .es-hidden {\n" +
                "                display: none !important\n" +
                "            }\n" +
                "\n" +
                "            tr.es-desk-hidden,\n" +
                "            td.es-desk-hidden,\n" +
                "            table.es-desk-hidden {\n" +
                "                width: auto !important;\n" +
                "                overflow: visible !important;\n" +
                "                float: none !important;\n" +
                "                max-height: inherit !important;\n" +
                "                line-height: inherit !important\n" +
                "            }\n" +
                "\n" +
                "            tr.es-desk-hidden {\n" +
                "                display: table-row !important\n" +
                "            }\n" +
                "\n" +
                "            table.es-desk-hidden {\n" +
                "                display: table !important\n" +
                "            }\n" +
                "\n" +
                "            td.es-desk-menu-hidden {\n" +
                "                display: table-cell !important\n" +
                "            }\n" +
                "\n" +
                "            .es-menu td {\n" +
                "                width: 1% !important\n" +
                "            }\n" +
                "\n" +
                "            table.es-table-not-adapt,\n" +
                "            .esd-block-html table {\n" +
                "                width: auto !important\n" +
                "            }\n" +
                "\n" +
                "            table.es-social {\n" +
                "                display: inline-block !important\n" +
                "            }\n" +
                "\n" +
                "            table.es-social td {\n" +
                "                display: inline-block !important\n" +
                "            }\n" +
                "\n" +
                "            .es-menu td a {\n" +
                "                font-size: 16px !important\n" +
                "            }\n" +
                "\n" +
                "            .es-desk-hidden {\n" +
                "                display: table-row !important;\n" +
                "                width: auto !important;\n" +
                "                overflow: visible !important;\n" +
                "                max-height: inherit !important\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body\n" +
                "    style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;padding:0;Margin:0\">\n" +
                "    <div class=\"es-wrapper-color\" style=\"background-color:#EFEFEF\"><!--[if gte mso 9]>\n" +
                "<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "<v:fill type=\"tile\" color=\"#efefef\"></v:fill>\n" +
                "</v:background>\n" +
                "<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#EFEFEF\">\n" +
                "            <tr style=\"border-collapse:collapse\">\n" +
                "                <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td class=\"es-adaptive\" align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-content-body\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#efefef;width:600px\"\n" +
                "                                    cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"Margin:0;padding-top:15px;padding-bottom:15px;padding-left:20px;padding-right:20px\">\n" +
                "                                            <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:270px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"left\" style=\"padding:15px;Margin:0\">\n" +
                "                                                                    <p\n" +
                "                                                                        style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">\n" +
                "                                                                        <br></p>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                            <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:270px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table><!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-header-body\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#fef5e4;width:600px\"\n" +
                "                                    cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#fef5e4\" align=\"center\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"Margin:0;padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px\">\n" +
                "                                            <!--[if mso]><table style=\"width:570px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:180px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td class=\"es-m-p0r\" valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:180px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td class=\"es-m-p0l es-m-txt-c\" align=\"left\"\n" +
                "                                                                    style=\"padding:0;Margin:0;padding-left:15px;font-size:0\">\n" +
                "                                                                    <a href=\"https://viewstripo.email/\" target=\"_blank\"\n" +
                "                                                                        style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#999999;font-size:14px\"><img\n" +
                "                                                                            src=\"https://siovhl.stripocdn.email/content/guids/CABINET_5ebd51945adb862745b1a105fbb2c4f4/images/431502878865957.png\"\n" +
                "                                                                            alt=\"Petshop logo\" title=\"Petshop logo\"\n" +
                "                                                                            width=\"118\"\n" +
                "                                                                            style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></a>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                            <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:370px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:370px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table><!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-radius:0px\"\n" +
                "                                                            width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"left\"\n" +
                "                                                                    style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:15px\">\n" +
                "                                                                    <h1\n" +
                "                                                                        style=\"Margin:0;line-height:18px;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;font-size:15px;font-style:normal;font-weight:normal;color:#333333\">\n" +
                "                                                                        <strong>親愛的顧客您好:</strong></h1>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-radius:0px\"\n" +
                "                                                            width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:15px\">\n" +
                "                                                                    <h1\n" +
                "                                                                        style=\"Margin:0;line-height:24px;mso-line-height-rule:exactly;font-family:'trebuchet ms', helvetica, sans-serif;font-size:20px;font-style:normal;font-weight:normal;color:#333333\">\n" +
                "                                                                         "+emailResponse.getBody()+"</h1>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"Margin:0;padding-top:20px;padding-left:20px;padding-right:20px;padding-bottom:30px\">\n" +
                "                                            <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td class=\"es-m-p20b\" align=\"left\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:280px\">\n" +
                "                                                        <table\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;background-color:#fef9ef;border-color:#efefef;border-width:1px 0px 1px 1px;border-style:solid\"\n" +
                "                                                            width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            bgcolor=\"#fef9ef\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                            <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:280px\">\n" +
                "                                                        <table\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;background-color:#fef9ef;border-width:1px;border-style:solid;border-color:#efefef\"\n" +
                "                                                            width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            bgcolor=\"#fef9ef\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\"\n" +
                "                                    align=\"center\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px\">\n" +
                "                                            <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;padding-bottom:10px;font-size:0\">\n" +
                "                                                                    <table width=\"100%\" height=\"100%\" cellspacing=\"0\"\n" +
                "                                                                        cellpadding=\"0\" border=\"0\" role=\"presentation\"\n" +
                "                                                                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                                        <tr style=\"border-collapse:collapse\">\n" +
                "                                                                            <td\n" +
                "                                                                                style=\"padding:0;Margin:0;border-bottom:1px solid #efefef;background:#FFFFFF none repeat scroll 0% 0%;height:1px;width:100%;margin:0px\">\n" +
                "                                                                            </td>\n" +
                "                                                                        </tr>\n" +
                "                                                                    </table>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FEF5E4;width:600px\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\" style=\"padding:20px;Margin:0\">\n" +
                "                                            <!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:178px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td class=\"es-m-p0r es-m-p20b\" valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:178px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td class=\"es-m-p0l es-m-txt-c\" align=\"left\"\n" +
                "                                                                    style=\"padding:0;Margin:0;font-size:0\"><img\n" +
                "                                                                        src=\"https://siovhl.stripocdn.email/content/guids/CABINET_5ebd51945adb862745b1a105fbb2c4f4/images/431502878865957.png\"\n" +
                "                                                                        alt=\"Petshop logo\" title=\"Petshop logo\"\n" +
                "                                                                        width=\"108\"\n" +
                "                                                                        style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\">\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                            <!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:362px\" valign=\"top\"><![endif]-->\n" +
                "                                            <table cellspacing=\"0\" cellpadding=\"0\" align=\"right\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td align=\"left\" style=\"padding:0;Margin:0;width:362px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            role=\"presentation\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"left\" style=\"padding:0;Margin:0\">\n" +
                "                                                                    <p\n" +
                "                                                                        style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:26px;color:#333333;font-size:17px\">\n" +
                "                                                                        <strong>聯絡方式:</strong></p>\n" +
                "                                                                    <p\n" +
                "                                                                        style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">\n" +
                "                                                                        <br></p>\n" +
                "                                                                    <p\n" +
                "                                                                        style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">\n" +
                "                                                                        <br></p>\n" +
                "                                                                </td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table><!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                    <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"\n" +
                "                        style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "                        <tr style=\"border-collapse:collapse\">\n" +
                "                            <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "                                <table class=\"es-content-body\"\n" +
                "                                    style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\"\n" +
                "                                    cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                    <tr style=\"border-collapse:collapse\">\n" +
                "                                        <td align=\"left\"\n" +
                "                                            style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px\">\n" +
                "                                            <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                <tr style=\"border-collapse:collapse\">\n" +
                "                                                    <td valign=\"top\" align=\"center\"\n" +
                "                                                        style=\"padding:0;Margin:0;width:560px\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n" +
                "                                                            style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                                                            <tr style=\"border-collapse:collapse\">\n" +
                "                                                                <td align=\"center\"\n" +
                "                                                                    style=\"padding:0;Margin:0;display:none\"></td>\n" +
                "                                                            </tr>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </table>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return html;
    }
}
