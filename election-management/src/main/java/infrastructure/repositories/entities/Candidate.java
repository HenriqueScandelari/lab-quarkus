package infrastructure.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Optional;

@Entity(name = "candidates")
public class Candidate {
    @Id
    private String id;

    private String photo;
    @Column(name = "given_name")
    private String givenName;
    @Column(name = "family_name")
    private String familyName;
    private String email;
    private String phone;
    @Column(name = "job_title")
    private String jobTitle;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public static Candidate fromDomain(domain.Candidate candidate) {
        Candidate entity = new Candidate();
        entity.setId(candidate.id());
        entity.setPhoto(candidate.photo().orElse(null));
        entity.setGivenName(candidate.givenName());
        entity.setFamilyName(candidate.familyName());
        entity.setEmail(candidate.email());
        entity.setPhone(candidate.phone().orElse(null));
        entity.setJobTitle(candidate.jobTitle().orElse(null));
        return entity;
    }

    public domain.Candidate toDomain() {
        return new domain.Candidate(getId(),
                Optional.ofNullable(getPhoto()),
                getGivenName(),
                getFamilyName(),
                getEmail(),
                Optional.ofNullable(getPhone()),
                Optional.ofNullable(getJobTitle()));
    }
}
