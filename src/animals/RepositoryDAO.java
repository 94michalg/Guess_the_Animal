package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

// class handles reading and saving database to the file
// it's Singleton
public class RepositoryDAO {
    private static final RepositoryDAO instance = new RepositoryDAO();
    private static final String FILE_NAME = "animals";
    private ObjectMapper objectMapper;
    private File file;

    // sets filetype when creating the instance
    private RepositoryDAO() {
        setFileType(Main.getFileType());
    }

    public static RepositoryDAO getInstance() {
        return instance;
    }

    // write the tree in the file
    public void writeFile(Node root) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read the tree from the file and returns root Node
    public Node readFile() {
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, Node.class);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Sets correct path to the file and proper object mapper
    // Default is "json"
    private void setFileType(String fileType) {
        switch (fileType) {
            case "xml":
                objectMapper = new XmlMapper();
                file = new File(String.format("%s.%s", FILE_NAME, fileType));
                break;
            case "yaml":
                objectMapper = new YAMLMapper();
                file = new File(String.format("%s.%s", FILE_NAME, fileType));
                break;
            default:
                objectMapper = new JsonMapper();
                file = new File(String.format("%s.%s", FILE_NAME, "json"));
                break;
        }
    }


}
