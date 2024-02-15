import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommentTest {

    private Comment comment;
    private User author;
    private String content;

    @Before
    public void setUp() {
        author = new User("John", "Doe", "nama", 0); // Example user
        content = "This is a comment.";
        comment = new Comment(author, content);
    }

    @Test
    public void testGetAuthor() {
        assertEquals(author, comment.getAuthor());
    }

    @Test
    public void testGetContent() {
        assertEquals(content, comment.getContent());
    }

    @Test
    public void testSetAuthor() {
        User newAuthor = new User("Jane", "Doe","ddddd", 0); // New user
        comment.setAuthor(newAuthor);
        assertEquals(newAuthor, comment.getAuthor());
    }

    @Test
    public void testSetContent() {
        String newContent = "Updated comment content.";
        comment.setContent(newContent);
        assertEquals(newContent, comment.getContent());
    }

    @Test
    public void testConstructor() {
        assertNotNull(comment);
        assertEquals(author, comment.getAuthor());
        assertEquals(content, comment.getContent());
    }
}
