package edu.ulatina.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name = "fileUploadView")
@ViewScoped
public class FileUploadView {
    
    private UploadedFile file;
    private InputStream fileInput;
    
    @ManagedProperty("#{crudViewADMIN}")
    private CrudViewAdmin crudView;
    
    public InputStream getFileInput() {
        return fileInput;
    }
    
    public void setFileInput(InputStream fileInput) {
        this.fileInput = fileInput;
    }
    
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public CrudViewAdmin getCrudView() {
        return crudView;
    }
    
    public void setCrudView(CrudViewAdmin crudView) {
        this.crudView = crudView;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try {
            this.file = null;
            this.file = event.getFile();
            this.fileInput = this.getFile().getInputStream();
            String namobreArchivoGuardar = this.getFileNameFormat(this.fileInput, file.getFileName());
            this.copyFileInFileSystem(this.fileInput, "C:\\Users\\sebas\\OneDrive\\Escritorio\\Proyecto II\\ProyectoII-FIJO\\ProyectoSoftwareII\\src\\main\\webapp\\resources\\images", namobreArchivoGuardar);
            if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
                this.file = file;
                FacesMessage msg = new FacesMessage("Successful", this.file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                crudView.getProductoSelected().setFotoProducto(namobreArchivoGuardar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getFileNameFormat(InputStream input, String nameFile) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(cal.getTime());
        nameFile = dateStr + "-" + nameFile;
        return nameFile;
    }
    
    public void copyFileInFileSystem(InputStream input, String pathCopy, String fileName) throws FileNotFoundException, IOException {
        Path path = Paths.get(pathCopy, fileName);
        if (Files.exists(path.getParent())) {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.copy(input, path, REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        } else {
            try {
                System.out.println("PROCESS FILE PATH===> " + path);
                Files.createDirectories(path.getParent());
                try {
                    Files.copy(input, path, REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }
    
}
