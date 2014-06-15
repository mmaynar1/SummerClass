import sun.tools.jar.resources.jar;

public class PhoneTexting
{
    public static void main( String[] args )
    {
        String message = "The quick brown fox jumped over the lazing sleeping dogs";
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};
        int[] input = {2, 22, 222, 3, 33, 333, 4, 44, 444, 5, 55, 555, 6, 66, 666, 7, 77, 777, 7777, 8, 88, 888, 9, 99, 999, 9999, 1};
        message = message.toLowerCase();
        for (int letterIndex = 0; letterIndex < message.length(); ++letterIndex)
        {
            for (int i = 0; i < (characters.length ); i++)
            {
                if ( message.charAt( letterIndex ) == characters[i])
                {
                    System.out.print(input[i]);
                }
            }
        }
    }
}
