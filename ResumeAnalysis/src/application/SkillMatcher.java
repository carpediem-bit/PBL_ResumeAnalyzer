package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillMatcher {
    private static final Map<String, List<String>> JOB_SKILLS = new HashMap<>();

    static {
        JOB_SKILLS.put("Java Developer", Arrays.asList(
                "Java", "Spring", "Spring Boot", "Hibernate", "OOP", "REST", "Microservices", "Maven", "JUnit"
        ));

        JOB_SKILLS.put("Data Analyst", Arrays.asList(
                "Python", "SQL", "Excel", "Tableau", "PowerBI", "Statistics", "Data Cleaning", "Pandas", "R"
        ));

        JOB_SKILLS.put("DevOps Engineer", Arrays.asList(
                "Docker", "CI/CD", "Kubernetes", "Linux", "AWS", "Terraform", "Shell Scripting", "Jenkins", "Monitoring"
        ));

        JOB_SKILLS.put("Frontend Developer", Arrays.asList(
                "HTML", "CSS", "JavaScript", "React", "Bootstrap", "Figma", "Responsive Design", "AJAX", "Webpack"
        ));

        JOB_SKILLS.put("Backend Developer", Arrays.asList(
                "Node.js", "Express", "Java", "Spring Boot", "REST APIs", "SQL", "MongoDB", "Security", "Authentication"
        ));

        JOB_SKILLS.put("Machine Learning Engineer", Arrays.asList(
                "Python", "Scikit-learn", "TensorFlow", "Keras", "Deep Learning", "Pandas", "NumPy", "Jupyter", "Model Deployment"
        ));

        JOB_SKILLS.put("Android Developer", Arrays.asList(
                "Java", "Kotlin", "Android Studio", "XML", "Firebase", "Jetpack Compose", "SQLite", "APIs"
        ));

        JOB_SKILLS.put("Cloud Engineer", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Docker", "Kubernetes", "Terraform", "Cloud Security", "CI/CD", "Linux"
        ));

        JOB_SKILLS.put("Cybersecurity Analyst", Arrays.asList(
                "Network Security", "Firewalls", "Ethical Hacking", "Penetration Testing", "Nmap", "Wireshark", "SIEM", "Python"
        ));
    }


    public static List<String> suggestRoles(String text) {
        List<String> roles = new ArrayList<>();
        for (String role : JOB_SKILLS.keySet()) {
            int match = 0;
            for (String skill : JOB_SKILLS.get(role)) {
                if (text.toLowerCase().contains(skill.toLowerCase())) {
                    match++;
                }
            }
            if (match >= 2) {
                roles.add(role);
            }
        }
        return roles;
    }
}
