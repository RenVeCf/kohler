package com.mengyang.kohler.whole_category.view;

import com.mengyang.kohler.R;
import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardDataImpl implements ECCardData<String> {

    private String cardTitle;
    private Integer mainBackgroundResource;
    private Integer headBackgroundResource;
    private List<String> listItems;

    public CardDataImpl(String cardTitle, Integer mainBackgroundResource, Integer headBackgroundResource, List<String> listItems) {
        this.mainBackgroundResource = mainBackgroundResource;
        this.headBackgroundResource = headBackgroundResource;
        this.listItems = listItems;
        this.cardTitle = cardTitle;
    }

    @Override
    public Integer getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    @Override
    public Integer getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    @Override
    public List<String> getListItems() {
        return listItems;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public static List<ECCardData> generateExampleData() {
        List<ECCardData> list = new ArrayList<>();
        list.add(new CardDataImpl("Card 1", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 1")));
        list.add(new CardDataImpl("Card 2", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 2")));
        list.add(new CardDataImpl("Card 3", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 3")));
        list.add(new CardDataImpl("Card 4", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 4")));
        list.add(new CardDataImpl("Card 5", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 5")));
        list.add(new CardDataImpl("Card 6", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 6")));
        list.add(new CardDataImpl("Card 7", R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, createItemsList("Card 7")));
        return list;
    }

    private static List<String> createItemsList(String cardName) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(
                cardName + " - Item 1",
                cardName + " - Item 2",
                cardName + " - Item 3",
                cardName + " - Item 4",
                cardName + " - Item 5",
                cardName + " - Item 6",
                cardName + " - Item 7"
        ));
        return list;
    }

}