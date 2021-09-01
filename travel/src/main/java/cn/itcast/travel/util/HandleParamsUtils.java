package cn.itcast.travel.util;

import javax.servlet.http.HttpServletRequest;

public class HandleParamsUtils {

    /**
     * 转换String类的当前页码为int类型
     * @param request
     * @param defaultValue
     * @return
     */
    public static int setCurrentPage(HttpServletRequest request,int defaultValue){
        //1.获取当前页码currentPage
        String currentPageStr = request.getParameter("currentPage");
        //2.转换为int类型
        int currentPage;
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            //设置为默认值第一页
            currentPage=defaultValue;
        }
        return currentPage;
    }

    /**
     * 转换String类的每页显示条数为int类型
     * @param request
     * @param defaultValue
     * @return
     */
    public static int setPageSize(HttpServletRequest request,int defaultValue){
        //1.获取每页显示条数pageSize
        String pageSizeStr = request.getParameter("pageSize");
        //2.转换为int类型
        int pageSize;
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else {
            //默认显示条数
            pageSize=defaultValue;
        }
        return pageSize;
    }

    /**
     * 转换String类型的cid(分类号)为int类型
     * @param request
     * @return
     */
    public static int setCid(HttpServletRequest request){
        //1.获取cid 分类id号
        String cidStr = request.getParameter("cid");
        int cid=0;
        //2.转为int类型
        if(cidStr!=null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid=Integer.parseInt(cidStr);
        }
        return cid;
    }

    /**
     * 将String类的price转换为double类型
     * @param priceStr
     * @return
     */
    public static double setPrice(String priceStr){
        double price=0.0;
        if(priceStr!=null && priceStr.length()>0 && !"null".equals(priceStr)){
            price=Double.parseDouble(priceStr);
        }
        return price;
    }
}
