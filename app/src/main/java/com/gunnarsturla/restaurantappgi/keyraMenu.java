package com.gunnarsturla.restaurantappgi;

/**
 * Created by Dagny on 14.10.2014.
 */

import java.util.Vector;

/**
 * @author Dagný Lára Guðmundsdóttir
 * @since 14.10.14
 * Tímabundinn klasi þar sem við harðkóðum innihald matseðilsins.
 * Seinna munum við ná í innihaldið úr xml skrá með SAX.
 */
public class keyraMenu {

    public static Vector<SubMenu> build() {

        //public Item(int id, int price, int cals, String name, String description, String ingredients, String imghash)

        Item item1 = new Item(1, 1999, 520, "Humarhalar", "Hvítlaukssteiktir humarhalar, bornir fram með heimabökuðu brauði og þremur tegundum af smjöri", "Rjómi, skelfiskur, allskonar", "11");
        Item item2 = new Item(2, 2490, 920, "Hamborgari", "Sýrður rjómi, Maríbó ostur og kaffi BBQ. Franskar kartöflur með kryddjurtum og Vesturbæjar mæjó ", "Kal, gúrka, tómatur, kjöt", "12");
        Item item3 = new Item(item1);
        Item item4 = new Item(item2);
        Item item5 = new Item(3,499, 100, "Danskt Rúgbrauð", "Með osti og smjöri", "Ostur, smjör, rúgur, hveiti", "13");
        Item item6 = new Item(4, 799, 500, "Samloka í Grófu Brauði", "Geitaostur, romain salat, klettasalat pestó og rauðrófugljái", "Geitaostur, brauð, klettasalat, furuhnetur", "14");

        //public SubMenu(String name, String description, String imghash)

        SubMenu sm1 = new SubMenu("Morgunverður", "", "20");
        SubMenu sm2 = new SubMenu("Smáréttir", "", "21");
        SubMenu sm3 = new SubMenu("Forréttir", "", "22");
        SubMenu sm4 = new SubMenu("Aðalréttir", "", "23");
        SubMenu sm5 = new SubMenu("Eftirréttir", "", "24");

        if (!sm1.addItem(item1)) {
            System.out.println("Error: ekki tókst að bæta " + item1.getName() + " við í SubMenu " + sm1.getName());
        }
        if (!sm2.addItem(item2)) {
            System.out.println("Error: ekki tókst að bæta " + item2.getName() + " við í SubMenu " + sm2.getName());
        }
        if (!sm3.addItem(item3)) {
            System.out.println("Error: ekki tókst að bæta " + item3.getName() + " við í SubMenu " + sm3.getName());
        }
        if (!sm3.addItem(item4)) {
            System.out.println("Error: ekki tókst að bæta " + item4.getName() + " við í SubMenu " + sm3.getName());
        }

        if (!sm4.addItem(item5)) {
            System.out.println("Error: ekki tókst að bæta " + item5.getName() + " við í SubMenu " + sm4.getName());
        }

        if (!sm5.addItem(item6)) {
            System.out.println("Error: ekki tókst að bæta " + item6.getName() + " við í SubMenu " + sm5.getName());
        }

        Vector<SubMenu> wm = new Vector<SubMenu>();

        if (!wm.add(sm1)) {
            System.out.println("Error: ekki tókst að bæta" + sm1.getName() + "við í W8rMenu");
        }
        if (!wm.add(sm2)) {
            System.out.println("Error: ekki tókst að bæta" + sm2.getName() + "við í W8rMenu");
        }
        if (!wm.add(sm3)) {
            System.out.println("Error: ekki tókst að bæta" + sm3.getName() + "við í W8rMenu");
        }

        if (!wm.add(sm4)) {
            System.out.println("Error: ekki tókst að bæta" + sm4.getName() + "við í W8rMenu");
        }

        if (!wm.add(sm5)) {
            System.out.println("Error: ekki tókst að bæta" + sm5.getName() + "við í W8rMenu");
        }

        return wm;
    }


}
