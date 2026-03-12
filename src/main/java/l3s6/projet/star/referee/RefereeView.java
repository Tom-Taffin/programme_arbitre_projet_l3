package l3s6.projet.star.referee;

import l3s6.projet.star.interaction.role.Role;
import l3s6.projet.star.interaction.view.AdminView;

import java.net.URISyntaxException;

public class RefereeView extends AdminView {
    public RefereeView(String ipAddress, int port, String id) throws URISyntaxException, InterruptedException {
        super(ipAddress, port, id);
    }

    @Override
    public void updateOnPlace(String id, String idPrime, String tile, int x, int y) {
        if (!this.roleManager.isRole(id, Role.PLAYER) ){
            return;
        }

        if (id != idPrime){
            //BLamer le joueur
        }



    }
}
