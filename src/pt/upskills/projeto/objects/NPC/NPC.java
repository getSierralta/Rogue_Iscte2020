package pt.upskills.projeto.objects.NPC;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class NPC {

    public void act(){
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.youtube.com/watch?v=QtBDL8EiNZo"));
        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

    }

}
