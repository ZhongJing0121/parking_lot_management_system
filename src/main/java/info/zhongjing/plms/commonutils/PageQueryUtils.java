package info.zhongjing.plms.commonutils;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description 自己写的分页插件 </p>
 * @date 2021/3/12 7:43 下午 </p>
 */
public class PageQueryUtils<T> {

    // 每页显示的条数
    private Integer pageSize;

    // 当前页码数
    private Integer pageNum;

    // 总页码数
    private Integer pageTotal;

    // 上一页页码数
    private Integer prePageNum;

    // 下一页页码数
    private Integer nextPageNum;

    // 要分页的数据
    private List<T> list;

    public PageQueryUtils() {
    }

    public PageQueryUtils(Integer pageNum, Integer pageSize, List<T> list) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.list = list;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPrePageNum() {
        return prePageNum;
    }

    public void setPrePageNum(Integer prePageNum) {
        this.prePageNum = prePageNum;
    }

    public Integer getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(Integer nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    // 分页
    public void page() {
        // 数据总条数
        int totalData = list.size();

        // 定义一个偏移量
        int pageSizeOffset = 0;

        int startPage = 0;
        int endPage = 0;

        // 数据量过小
        if (totalData == 0 || totalData <= pageSize) {
            pageSize = totalData;
            pageTotal = 1;
            prePageNum = 0;
            pageNum = 1;
            nextPageNum = 2;
            endPage = totalData;
        } else {
            // 计算得出总页数
            pageTotal = totalData % pageSize == 0 ? totalData / pageSize : totalData / pageSize + 1;

            // 分页合理化
            if (pageNum <= 0) {
                pageNum = 1;
            }
            if (pageNum >= pageTotal) {
                pageNum = pageTotal;
                if (totalData % pageSize != 0) {
                    pageSizeOffset = pageSize - totalData % pageSize;
                }
            }

            // 计算上一页的页码数
            prePageNum = pageNum - 1;
            // 计算下一页的页码数
            nextPageNum = pageNum + 1;


            // 进行分页数据截取
            /*
            1 0 5
            2 5 10
            3 10 15
            */
            startPage = (pageNum - 1) * pageSize;
            endPage = (pageNum - 1) * pageSize + pageSize - pageSizeOffset;
        }


        list = list.subList(startPage, endPage);
    }

}
