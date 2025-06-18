package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobCompanyMap {

    private static final Map<String, List<String>> roleToCompanies = new HashMap<>();
    private static final Map<String, List<String>> companySkills = new HashMap<>();

    static {
        // Java Developer
        roleToCompanies.put("Java Developer", Arrays.asList("TCS", "Infosys", "Google", "Capgemini", "Accenture"));
        companySkills.put("TCS", Arrays.asList("Java", "Spring Boot", "Hibernate", "REST", "JPA", "JUnit", "Microservices"));
        companySkills.put("Infosys", Arrays.asList("Java", "Spring", "MySQL", "OOP", "SOAP", "Git", "Agile"));
        companySkills.put("Google", Arrays.asList("Java", "Cloud", "Kubernetes", "BigQuery", "GCP", "CI/CD", "Distributed Systems"));
        companySkills.put("Capgemini", Arrays.asList("Java", "Spring Boot", "Hibernate", "Microservices", "AWS", "REST API"));
        companySkills.put("Accenture", Arrays.asList("Java", "PL/SQL", "Spring", "Microservices", "JUnit", "Bitbucket"));

        // Data Analyst
        roleToCompanies.put("Data Analyst", Arrays.asList("Zoho", "Amazon", "Flipkart", "Cognizant", "Wipro"));
        companySkills.put("Zoho", Arrays.asList("Excel", "SQL", "Python", "Tableau", "Data Visualization", "PowerBI", "Statistics"));
        companySkills.put("Amazon", Arrays.asList("SQL", "Python", "Redshift", "Tableau", "ETL", "Data Cleaning", "Big Data"));
        companySkills.put("Flipkart", Arrays.asList("PowerBI", "Excel", "Python", "Google Sheets", "Data Wrangling", "Pandas"));
        companySkills.put("Cognizant", Arrays.asList("Data Mining", "SAS", "Excel", "PowerBI", "Business Intelligence", "ETL"));
        companySkills.put("Wipro", Arrays.asList("SQL", "Excel", "Python", "Visualization", "Google Looker", "Data Prep"));

        // DevOps Engineer
        roleToCompanies.put("DevOps Engineer", Arrays.asList("Microsoft", "IBM", "Oracle", "Deloitte", "Cisco"));
        companySkills.put("Microsoft", Arrays.asList("Azure", "Terraform", "CI/CD", "DevOps", "YAML", "Docker", "Monitoring Tools"));
        companySkills.put("IBM", Arrays.asList("Linux", "Kubernetes", "Jenkins", "Shell Scripting", "Nagios", "Docker", "Prometheus"));
        companySkills.put("Oracle", Arrays.asList("Cloud Infrastructure", "Terraform", "Docker", "CI/CD", "GitOps", "Helm Charts"));
        companySkills.put("Deloitte", Arrays.asList("AWS", "Ansible", "Kubernetes", "Docker", "Git", "Shell", "Cloud Security"));
        companySkills.put("Cisco", Arrays.asList("CI/CD", "Jenkins", "Infrastructure as Code", "Linux", "DevOps", "Cloud Automation"));

        // Frontend Developer
        roleToCompanies.put("Frontend Developer", Arrays.asList("Google", "Adobe", "Zoho", "Swiggy", "Zomato"));
        companySkills.put("Adobe", Arrays.asList("React", "HTML", "CSS", "JavaScript", "UX Design", "TypeScript", "Webpack"));
        companySkills.put("Swiggy", Arrays.asList("Vue.js", "HTML", "CSS", "SASS", "Figma", "JSX", "React Native"));
        companySkills.put("Zomato", Arrays.asList("HTML5", "Bootstrap", "Angular", "REST API Integration", "JavaScript", "Responsive Design"));

        // Machine Learning Engineer
        roleToCompanies.put("Machine Learning Engineer", Arrays.asList("NVIDIA", "Google", "Samsung R&D", "Fractal Analytics", "Tata Elxsi"));
        companySkills.put("NVIDIA", Arrays.asList("TensorFlow", "PyTorch", "Python", "Deep Learning", "C++", "Computer Vision", "CUDA"));
        companySkills.put("Samsung R&D", Arrays.asList("Python", "Keras", "Sklearn", "ML Algorithms", "OpenCV", "Data Engineering"));
        companySkills.put("Fractal Analytics", Arrays.asList("Pandas", "Feature Engineering", "Model Deployment", "Hyperparameter Tuning", "Jupyter"));

        // Android Developer
        roleToCompanies.put("Android Developer", Arrays.asList("Samsung", "Google", "CRED", "Paytm", "Freshworks"));
        companySkills.put("Samsung", Arrays.asList("Java", "Kotlin", "Android Studio", "Jetpack Compose", "Firebase", "Room DB"));
        companySkills.put("CRED", Arrays.asList("Android Jetpack", "MVVM", "Retrofit", "Kotlin Coroutines", "Unit Testing", "Figma Handoff"));

        // Cloud Engineer
        roleToCompanies.put("Cloud Engineer", Arrays.asList("Infosys", "AWS", "Azure", "Google Cloud", "Accenture"));
        companySkills.put("AWS", Arrays.asList("EC2", "Lambda", "S3", "IAM", "VPC", "Terraform", "Bash", "CloudWatch"));
        companySkills.put("Azure", Arrays.asList("Azure Functions", "ARM Templates", "Networking", "RBAC", "DevOps Pipelines"));
        companySkills.put("Google Cloud", Arrays.asList("GKE", "Cloud Run", "BigQuery", "Pub/Sub", "IAM", "Terraform"));

        // Cybersecurity Analyst
        roleToCompanies.put("Cybersecurity Analyst", Arrays.asList("TCS", "EY", "KPMG", "Infosys", "Wipro"));
        companySkills.put("EY", Arrays.asList("SIEM", "SOC Operations", "Incident Response", "Python", "Splunk", "Security Audits"));
        companySkills.put("KPMG", Arrays.asList("Ethical Hacking", "Penetration Testing", "Burp Suite", "Nmap", "Metasploit", "ISO 27001"));
    }

    public static List<String> getCompaniesForRole(String role) {
        return roleToCompanies.getOrDefault(role, List.of());
    }

    public static List<String> getSkillsForCompany(String company) {
        return companySkills.getOrDefault(company, List.of());
    }
}

