# Smart Resume Analysis Tool  
A smart resume analysis tool that analyzes your resume and provides you the following benefits:  
&emsp; 1. Suggests career paths based on the skills in your resume  
&emsp; 2. You can choose a target company and the tool will provide you a list of skills that you require for the position.  

## User Guide
To run the application, just open the main.jar file and the application GUI will be visible.  

The user must enter the resume by clicking on the 'Browse Resume' button to browse for the resume file in the local file system.  
Once entered, all the skills and job roles will be extracted and posted onto the stage along with the appropriate job roles for them.


If you desire to check the missing skills that you require to apply to a certain firm/company, you may choose the company from a scrollable list of companies.  
Upon choosing a company, a list of skills missing from your resume for the company will be posted.


## Overview
A GUI application developed using JavaFX and backend development using Java.  
&emsp;1. The application takes a resume pdf or docx file as an input and parses it to text using ResumeParser, for the analysis.  
&emsp;2. All the skills from the file are extracted and parsed to SkillMatcher to find the appropriate job roles.  
&emsp;3. The potential employers are then extracted from the job roles acquired by parsing them to JobCompanyMap and then displayed.  
&emsp;4. If a target company is selected, all the skills that are missing from resume, for the particular role, are posted onto the stage.
