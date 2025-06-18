module ResumeAnalysis {
	requires javafx.controls;
	requires javafx.graphics;
	requires org.jsoup;
	requires java.sql;
	requires org.apache.pdfbox;
	requires org.apache.poi.ooxml;
	
	opens application to javafx.graphics, javafx.fxml;
}
