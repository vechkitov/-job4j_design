package ru.job4j.ood.isp.menu;

public class MenuPrinterImpl implements MenuPrinter {

    /**
     * Выводит в консоль содержание меню в виде списка с отступами. Размер отступа
     * зависит от номера элемента меню.
     *
     * @param menu
     */
    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo itemInfo : menu) {
            String number = itemInfo.getNumber();
            long count = number.chars()
                    .filter(ch -> ch == '.')
                    .count();
            System.out.print(
                    itemInfo.toString().indent(((int) count - 1) * 2)
            );
        }
    }
}
