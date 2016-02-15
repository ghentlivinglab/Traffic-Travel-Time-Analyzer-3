import org.junit.*;
import scrapers.Foo;

public class TestClass
{
    private Foo klasse = new Foo();

    @Test
    public void testShouldBeOk()
    {

        Assert.assertEquals(1,klasse.returnone());
    }

    @Test
    public void testShouldFail()
    {

        Assert.assertNotEquals(0,klasse.returnone());
    }
}