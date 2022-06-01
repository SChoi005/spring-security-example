package project.enumeration;

import lombok.Getter;

@Getter
public enum Role{
    
    ROLE_PROFESSOR("professor, student"), ROLE_STUDENT("student");

    private String description;
    
    Role(String description){
        this.description = description;
    }
}