package l3s6.projet.star.referee;

import l3s6.projet.star.interaction.view.AdminView;

import java.net.URISyntaxException;
import java.util.List;

public class RefereeView extends AdminView {
    public RefereeView(String ipAddress, int port, String id) throws URISyntaxException, InterruptedException {
        super(ipAddress, port, id);
    }

    @Override
    public void updateOnGrant(String s, String s1, List list) {

    }

    @Override
    public void updateOnPlace(String s, String s1, String s2, int i, int i1) {

    }

    @Override
    public void updateOnPlaceWithMeeple(String s, String s1, String s2, int i, int i1, String s3, String s4) {

    }

    @Override
    public void updateOnOffer(String s, String s1, String s2) {

    }

    @Override
    public void updateOnEnter(String s) {

    }

    @Override
    public void updateOnLeave(String s) {

    }

    @Override
    public void updateOnClose(String s) {

    }

    @Override
    public void updateOnExpel(String s, String s1) {

    }

    @Override
    public void updateOnScore(String s, String s1, int i) {

    }

    @Override
    public void updateOnStart(String s) {

    }

    @Override
    public void updateOnEnd(String s, List list) {

    }

    @Override
    public void updateOnAgree(String s, List list) {

    }

    @Override
    public void updateOnElect(String s, String s1, List list) {

    }
}
