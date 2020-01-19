package pe.com.apptama.apptamadirectorio.presentacion.model;

public class ContactModel {


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private String phone;
    private int photo;

    public ContactModel() {
    }

    public ContactModel(String name, String phone, int photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactModel that = (ContactModel) o;

        return phone != null ? phone.equals(that.phone) : that.phone == null;
    }

    @Override
    public int hashCode() {
        return phone != null ? phone.hashCode() : 0;
    }
}
