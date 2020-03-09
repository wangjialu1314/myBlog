package com.wjl.blog.service.impl;

import com.wjl.blog.entity.BlogContentBean;
import com.wjl.blog.entity.BlogSaveFailBean;
import com.wjl.blog.entity.BlogTypeBean;
import com.wjl.blog.mapper.EditerMapper;
import com.wjl.blog.service.EditerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service(value = "EditerService")
public class EditerServiceImpl implements EditerService {

    private static final Logger log = LoggerFactory.getLogger(EditerServiceImpl.class);

    private static final ThreadLocal<DateFormat> dateTime = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        }
    };

    @Resource
    private EditerMapper editerMapper;

    /**
     * 添加博客内容
     * @param blogContentBean
     * @return
     */
    @Override
    public boolean insertBlogContert(BlogContentBean blogContentBean) {
        // 填写博客信息
        String blogId = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        blogContentBean.setId(blogId);
        blogContentBean.setStartTime(dateTime.get().format(new Date()));
        blogContentBean.setState("1");
        return editerMapper.insertBlogContert(blogContentBean);
    }

    /**
     * 查询修改的博客信息
     * @return
     */
    @Override
    public List<BlogContentBean> queryBlogContentList(String type) {
        return editerMapper.queryBlogContentList(type);
    }

    /**
     * 查询博客单个内容
     * @param id
     * @return
     */
    @Override
    public BlogContentBean queryBlogContent(String id) {
        return editerMapper.queryBlogContent(id);
    }

    /**
     * 修改博客信息
     * @param blogContentBean
     * @return
     */
    @Override
    public boolean updateBlogContent(BlogContentBean blogContentBean) {
        blogContentBean.setEndTime(dateTime.get().format(new Date()));
        int i = editerMapper.updateBlogContent(blogContentBean);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除博客信息
     * @param id
     * @return
     */
    @Override
    public boolean deleteBlogInfo(String id) {
        String time = dateTime.get().format(new Date());
        int i =editerMapper.deleteBlogInfo(id, time);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    /** 
    * @Description: 保存消息队列失败内容
    * @Param: [blogSaveFailBean] 
    * @return: boolean 
    * @Author: wangjialu
    * @Date: 2020/2/17 
    */ 
    @Override
    public boolean insertBlogSaveFail(BlogSaveFailBean blogSaveFailBean) {
        return editerMapper.insertBlogSaveFail(blogSaveFailBean);
    }

    /** 
    * @Description: 查询博客发布类型
    * @Param: [blogWriteType] 
    * @return: java.util.List<com.wjl.blog.entity.BlogTypeBean> 
    * @Author: wangjialu
    * @Date: 2020/3/9 
    */ 
    @Override
    public List<BlogTypeBean> queryBlogTypeList(String blogWriteType) {
        return editerMapper.queryBlogTypeList(blogWriteType);
    }
}
