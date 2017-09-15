# Parameter instruction

Parameter | Implication | Limited optional | Remark
---|---|---|---
mail.hostname | generalMailSender used mail server |
mail.port | generalMailSender used port of mail server |
mail.username | generalMailSender user name |
mail.password | generalMailSender authentication code |
mail.from | generalMailSender sender |
mail.fromname | generalMailSender display name of the sender |
**mail.sender** | Mail sending method used by Shepher | generalMailSender/customMailSender | customMailSender is self-defined mail type, reference CustomMailSender
**cas.server.url.prefix** | CAS authentication server address |
cas.login.url | CAS authentication server login access |
cas.logout.url | CAS authentication server logout access |
**ldap.url** | LDAP authentication server address |
ldap.password | LDAP authentication password |
ldap.dn | LDAP authentication DN |
**server.url** | Shepher server address |
**server.port** | Shepher server port |
**mail.mailToSuffix** | Suffix of the address of a mail receiver |
mail.mailAddressEndSeparator | Separator for many receivers of a mail |
**server.login.type** | Shepher login type | CAS/LDAP | Only CAS and LDAP are supported at present