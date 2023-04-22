package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    /**
     * Добавляет элемент меню в меню.
     *
     * @param parentName     имя элемента-родителя. Если не передано, то создается элемент самого верхнего уровня
     * @param childName      имя добавляемого элемента
     * @param actionDelegate действие
     * @return true - элемент меню добавлен в меню; false - иначе
     */
    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        if (parentName == null) {
            rootElements.add(new SimpleMenuItem(childName, actionDelegate));
            return true;
        }
        Optional<ItemInfo> optParent = findItem(parentName);
        if (optParent.isEmpty()) {
            throw new IllegalArgumentException("Не найден родительский элемент меню с именем " + parentName);
        }
        optParent.get().addChild(new SimpleMenuItem(childName, actionDelegate));
        return true;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        return findItem(itemName).map(
                itemInfo -> new MenuItemInfo(itemInfo.getMenuItem(), itemInfo.getNumber())
        );
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        return new Iterator<>() {
            private final DFSIterator dfsIterator = new DFSIterator();

            @Override
            public boolean hasNext() {
                return dfsIterator.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                ItemInfo itemInfo = dfsIterator.next();
                return new MenuItemInfo(itemInfo.getMenuItem(), itemInfo.getNumber());
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Не передано имя элемента меню.");
        }
        Optional<ItemInfo> res = Optional.empty();
        DFSIterator it = new DFSIterator();
        while (it.hasNext()) {
            ItemInfo itemInfo = it.next();
            if (itemInfo.getMenuItemName().equals(name)) {
                res = Optional.of(itemInfo);
                break;
            }
        }
        return res;
    }

    private static class SimpleMenuItem implements MenuItem {

        private final String name;
        private final List<MenuItem> children = new ArrayList<>();
        private final ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {
        private final Deque<MenuItem> stack = new LinkedList<>();
        private final Deque<String> numbers = new LinkedList<>();

        public DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {
        private final MenuItem menuItem;
        private final String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }

        public String getMenuItemName() {
            return menuItem.getName();
        }

        public void addChild(MenuItem child) {
            menuItem.getChildren().add(child);
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public String getNumber() {
            return number;
        }
    }
}
