package com.mengyang.kohler.module.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.common.entity.Level1Item;

import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

public class QuestionSearchBean  implements MultiItemEntity{

    public QuestionSearchBean(int itemType) {
        this.itemType = itemType;
    }

    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    private List<ListItem> listItem;
    private QuestionSearch questionSearch;

    public List<ListItem> getListItem() {
        return listItem;
    }

    public void setListItem(List<ListItem> listItem) {
        this.listItem = listItem;
    }

    public QuestionSearch getQuestionSearch() {
        return questionSearch;
    }

    public void setQuestionSearch(QuestionSearch questionSearch) {
        this.questionSearch = questionSearch;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    /**
     * h5Url : http://www.baidu.com
     * description : 您好,您的桌号可能是1,或请查看桌号名册
     * isTable : true
     */


    public static class QuestionSearch  implements MultiItemEntity{
        private String h5Url;
        private String description;
        private boolean isTable;
        private int itemType;

        public QuestionSearch(String description, int itemType) {
            this.description = description;
            this.itemType = itemType;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isIsTable() {
            return isTable;
        }

        public void setIsTable(boolean isTable) {
            this.isTable = isTable;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

    public static class ListItem extends AbstractExpandableItem<ListItem.ListItemChild> implements MultiItemEntity{
        private int itemType;
        private String parentLeft;
        private String parentRight;
        private List<ListItemChild> listItemChild;

        @Override
        public int getLevel() {
            return 0;
        }

        public List<ListItemChild> getListItemChild() {
            return listItemChild;
        }

        public void setListItemChild(List<ListItemChild> listItemChild) {
            this.listItemChild = listItemChild;
        }

        public String getParentLeft() {
            return parentLeft;
        }

        public void setParentLeft(String parentLeft) {
            this.parentLeft = parentLeft;
        }

        public String getParentRight() {
            return parentRight;
        }

        public void setParentRight(String parentRight) {
            this.parentRight = parentRight;
        }

        public ListItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }


        public static class ListItemChild {
            private String itemChildContent;

            public String getItemChildContent() {
                return itemChildContent;
            }

            public void setItemChildContent(String itemChildContent) {
                this.itemChildContent = itemChildContent;
            }
        }
    }

}
