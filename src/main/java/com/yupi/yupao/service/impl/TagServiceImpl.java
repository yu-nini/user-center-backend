package com.yupi.yupao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yupao.mapper.TagMapper;
import com.yupi.yupao.model.domain.Tag;
import com.yupi.yupao.service.TagService;
import org.springframework.stereotype.Service;

/**
*
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
implements TagService {

}
