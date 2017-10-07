package com.log2n.rentafurniture;

/**
 * Created by shahrukh on 9/8/17.
 */

public class ProfileInfo {

        public String name;
        public String address;
        public String contact_no;
        public String email;
        public String password;
        public String upload_id;



        public String getName() {
            return name;
        }

        public String getAddress(){
            return address;
        }

        public String getContact_no(){
            return contact_no;
        }

        public String getEmail(){
            return email;
        }

         public String getPassword(){
             return password;
         }

    public String getUpload_id() {
        return upload_id;
    }

    public ProfileInfo(String name, String address, String contact_no, String email, String password,String upload_id){
            this.name=name;
            this.address=address;
            this.contact_no=contact_no;
            this.email=email;
            this.password=password;
            this.upload_id=upload_id;
        }

        public ProfileInfo(){}

}
