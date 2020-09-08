package ddbjld.api.data.beans;

import lombok.Data;

@Data
// JsonNamingは不要
public class AmUserInfo {
    private String _id;
    private String _rev;
    private String username;
    private String realm;
    private String[] uid;
    private String[] postalAddress;
    private String[] telephoneNumber;
    private String[] mail;
    private String[] universalid;
    private String[] givenName;
    private String[] objectClass;
    private String[] dn;
    private String[] sn;
    private String[] cn;
    private String[] employeeNumber;
}
