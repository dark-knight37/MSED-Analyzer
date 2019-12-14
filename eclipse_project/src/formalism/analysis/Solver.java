package formalism.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import formalism.features.Analyzable;
import formalism.utils.LabelGenerator;

/**
 * Solver
 * Date: 20.12.2015
 * @author Stefano Marrone
 */
public abstract class Solver {
    /**
     * name of the tool with path.
     */
    protected String toolName;

    /**
     * name of the working directory.
     */
    protected String workingDir;

    /**
     * constructor.
     * @param workingDir directory used for temporary files.
     * @param toolName name of the tool.
     */
    public Solver(String workingDir, String toolName) {
        this.workingDir = workingDir;
        this.toolName = toolName;
    }

    /**
     * tool name getter method.
     * @return name of the tool.
     */
    public String getToolName() {
        return this.toolName;
    }

    /**
     * tool name setter method.
     * @param toolName new name of the tool.
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * working directory getter method.
     * @return the working directory.
     */
    public String getWorkingDir() {
        return this.workingDir;
    }

    /**
     * working directory setter method.
     * @param workingDir new working directory.
     */
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public abstract void analyse(Analyzable a) throws Exception;
    
    protected String checkDir(String fill) throws Exception {
        File dir = new File(this.workingDir);
        if (!dir.exists())
            throw new Exception("The working directory doesn't exist!");
        if (!dir.isDirectory())
            throw new Exception("The working directory isn't a valid directory!");

        // Create the structure files
        File struct = null;
        FileOutputStream structStream = null;
        PrintStream printStruct = null;
        try {
            struct =  File.createTempFile(LabelGenerator.get("file"),null,dir);
            structStream = new FileOutputStream(struct);
            printStruct = new PrintStream(structStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printStruct.println(fill);
        String retval = struct.getName();
        structStream.close();
        return retval;
    }
}