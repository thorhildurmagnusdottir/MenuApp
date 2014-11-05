package com.gunnarsturla.restaurantappgi;

/**
 * Created by Dagný on 30.10.2014.
 */
public class Order {

    private static SubMenu order = new SubMenu();

    // Ný vara pöntuð

    public static boolean addOrder(Item item) {
        return order.addItem(item);
    }

    public static Item remove(int itemNo) {
        return order.removeItem(itemNo);
    }

	public static Item get(int i) { return order.get(i); }

    // Pöntunin sótt
    public static SubMenu getOrder() {
        return order;
    }

    public static int sum() {

        int sum = 0;
        for (int i = 0; i < order.size(); i++) {
            sum = sum + (order.get(i).getPrice());
        }
        return sum;
    }

    public static boolean isEmpty() {
        return order.isEmpty();
    }
    // skilar inn í OrderListAdapter
    public static int size() {
        return order.size();
    }
}


