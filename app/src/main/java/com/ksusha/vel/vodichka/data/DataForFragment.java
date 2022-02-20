package com.ksusha.vel.vodichka.data;

import com.ksusha.vel.vodichka.model.TopRecycler;
import com.ksusha.vel.vodichka.model.Water;

import java.util.ArrayList;
import java.util.List;

public class DataForFragment {

    public static List<Water> waterList = new ArrayList<>();
    public static List<Water> stockList = new ArrayList<>();
    public static List<Water> basketList;
    public static List<TopRecycler> topRecyclerList = new ArrayList<>();

    public static Boolean isBasket = false;

    public static int sumBasket;
    public static int countBasket;


    public static void initData() {
        countBasket = 0;
        sumBasket = 0;
        waterList.add(new Water("mf1", "categ1_wat", "Вода від 2-х бутлів (при наявності обмінної тари)",
                50, 2, 2, true, 0));
        waterList.add(new Water("mf2", "categ2_wat", "Вода(при наявності обмінної тари)",
                70, 1, 1, true, 0));
        waterList.add(new Water("mf3", "categ3_wat", "Бутель для води 18,9л.",
                200, 1, 2, true, 0));
        waterList.add(new Water("mf4", "categ4_acces", "Помпа механічна",
                120, 1, 1, true, 0));
        waterList.add(new Water("mf5", "categ5_acces", "Ручка для бутлів",
                50, 1, 2, true, 0));
        waterList.add(new Water("mf6", "categ6_acces", "Електрична помпа ViO E4",
                300, 1, 1, true, 0));
        waterList.add(new Water("mf7", "categ7_cool", "Кулер ViO 903",
                3000, 1, 2, true, 0));
        waterList.add(new Water("mf8", "categ8_cool", "Кулер ViO 903 білий",
                3000, 1, 1, true, 0));
        waterList.add(new Water("mf9", "categ9_cool", "Кулер ViO Х172",
                3000, 1, 2, true, 0));
        waterList.add(new Water("mf10", "categ10_cool", "Договір Оферти",
                1, 1, 1, true, 0));

        stockList.add(new Water("sf1", "stock1_wat", "Кулер у подарунок",
                1, 1, 1, true, 0));
        stockList.add(new Water("sf2", "stock2_wat", "Помпа безкоштовна",
                1, 1, 1, true, 0));

        topRecyclerList.add(new TopRecycler(1, "bottom_sheet_1", "Знижка при замовленні від двох бутлів", "При замовленні від двох бутлів води – вартість одного бутля всього 50грн!!!"));
        topRecyclerList.add(new TopRecycler(2, "bottom_sheet_2", "19л у подарунок!", "Замовите три бутля і отримаєте 19л у подарунок!* Для нових клієнтів: при одноразовому замовленні 3-х і більше бутлів, ми даруємо вам один бутель води! (Його вартість умовно вважається 0,01грн)."));
        topRecyclerList.add(new TopRecycler(3, "bottom_sheet_3", "Даруємо помпу для води", "Оформивши замовлення відразу на 5 бутлів, новий клієнт отримує в подарунок від компанії механічну помпу для води ()умовно за 0,01 грн."));
        topRecyclerList.add(new TopRecycler(4, "bottom_sheet_4", "Продаж, оренда та чищення кулерів", "Продаж, оренда та чищення кулерів максимально швидко та вигідно"));
        topRecyclerList.add(new TopRecycler(5, "bottom_sheet_5", "Кулер безкоштовно!", "Замовляйте 10 бутлів щотижня і користуйтеся кулером безкоштовно!* В заставу ви залишаєте всього 50% вартості обладнання. Вся сума буде повернута вам після закінчення терміну договору за умови, що кулер знаходиться у робочому стані."));
        topRecyclerList.add(new TopRecycler(6, "bottom_sheet_6", "Доставка води в Одесі", "Доставляємо очищену питну воду по всій Одесі"));
        topRecyclerList.add(new TopRecycler(7, "bottom_sheet_7", "Безкоштовна доставка", "Безкоштовна доставка від нашої компанії в зручний час до вашої домівки"));


    }

    public static void initDataBasket() {
        basketList = new ArrayList<>();
        for (Water w : waterList) {
            if (!w.getMaskClicable()) {
                basketList.add(w);
            }
        }
        for (Water w : stockList) {
            if (!w.getMaskClicable()) {
                basketList.add(w);
            }
        }
    }

    public static int getSumBasket() {
        return sumBasket;
    }

    public static int getCountBasket() {
        return countBasket;
    }


    public static Boolean getBasket() {
        return isBasket;
    }

    public static void setBasket(Boolean basket) {
        isBasket = basket;
    }


}

