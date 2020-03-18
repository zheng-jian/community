package com.uga.zj.community.DataTransferObject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPreviousPage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;

    private Integer page;
    private List<Integer> pages = new ArrayList<>();


    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.page = page;
        Integer totalPage = 0;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }
        else{
            totalPage = totalCount / size +1;
        }

        if(page < 1)
            page =1;

        if(page > totalPage)
            page = totalPage;

        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page - i > 0)
                pages.add(0,page-i);
            if(page + i <=totalPage)
                pages.add(page+i);
        }


        //if show next page or previous page
        if(page == 1) {
            showNextPage = true;
        }
        else{
            showPreviousPage = true;
            showNextPage = true;
        }

        if(page == totalPage){
            showNextPage = false;
        }


        //if show the button of first page
        if(pages.contains(1)){
            showFirstPage = false;
        }
        else{
            showFirstPage = true ;
        }

        //if show the button of last page
        if(pages.contains(totalPage)){
            showEndPage = false;
        }
        else{
            showEndPage = true;
        }

    }
}
