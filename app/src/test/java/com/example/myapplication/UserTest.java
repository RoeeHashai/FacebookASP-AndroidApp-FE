import com.example.myapplication.R;
import com.example.myapplication.entities.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.net.Uri;

public class UserTest {

    private User user;
    private String userName;
    private String password;
    private String displayName;
    private int profilePicResource;
    private Uri profilePicUri;

    @Before
    public void setUp() {
        userName = "john_doe";
        password = "password123";
        displayName = "John Doe";
        profilePicResource = R.drawable.facebook_icon; // Example profile picture resource ID
        Uri imageUri = null;
        user = new User(userName, password, displayName, profilePicResource);
    }

    @Test
    public void testGetUserName() {
        assertEquals(userName, user.getUserName());
    }

    @Test
    public void testGetDisplayName() {
        assertEquals(displayName, user.getDisplayName());
    }

    @Test
    public void testGetProfilePicResource() {
        assertEquals(profilePicResource, user.getIntProfilePic());
    }

    @Test
    public void testGetProfilePicUri() {
        user.setProfilePic(profilePicUri);
        assertEquals(profilePicUri, user.getUriProfilePic());
    }

    @Test
    public void testMatchingPassword() {
        assertTrue(user.isMachingPassword(password));
    }

    @Test
    public void testNonMatchingPassword() {
        assertFalse(user.isMachingPassword("wrongPassword"));
    }

    @Test
    public void testSetProfilePicWithResourceId() {
        user.setProfilePic(profilePicResource);
        assertEquals(profilePicResource, user.getIntProfilePic());
        assertEquals(null, user.getUriProfilePic());
    }

    @Test
    public void testSetProfilePicWithUri() {
        user.setProfilePic(profilePicUri);
        assertEquals(profilePicUri, user.getUriProfilePic());
        assertEquals(0, user.getIntProfilePic());
    }
}
