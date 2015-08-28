package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SQATEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SQATEntity)) {
            return false;
        }
        SQATEntity other = (SQATEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sqat.Entity.SQATEntity[ id=" + id + " ]";
    }

    // variables
    String fileName = "";
    String className = "";
    String methodName = "";
    String classVariableName = "";
    String methodVariableName = "";
    long methodLineOfCode;
    private long methodBlankLines;
    private long methodTotalLines;
    private long methodCommentLines;

    public long getMethodLineOfCode() {
        return methodLineOfCode;
    }

    public void setMethodLineOfCode(int methodLineOfCode) {
        this.methodLineOfCode = methodLineOfCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassVariableName() {
        return classVariableName;
    }

    public void setClassVariableName(String classVariableName) {
        this.classVariableName = classVariableName;
    }

    public String getMethodVariableName() {
        return methodVariableName;
    }

    public void setMethodVariableName(String methodVariableName) {
        this.methodVariableName = methodVariableName;
    }

    public long getMethodBlankLines() {
        return methodBlankLines;
    }

    public void setMethodBlankLines(long methodBlankLines) {
        this.methodBlankLines = methodBlankLines;
    }

    public long getMethodTotalLines() {
        return methodTotalLines;
    }

    public void setMethodTotalLines(long methodTotalLines) {
        this.methodTotalLines = methodTotalLines;
    }

    public long getMethodCommentLines() {
        return methodCommentLines;
    }

    public void setMethodCommentLines(long methodCommentLines) {
        this.methodCommentLines = methodCommentLines;
    }

}
