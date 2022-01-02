package com.es.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created on 2021/12/13 15:29
 *
 * @author Marfack
 */
@SpringBootTest
public class CareerClassDaoTest {
    @Autowired
    CareerClassDao careerClassDao;

    @Autowired
    CareerInfoDao careerInfoDao;

    @Test
    @Transactional
    void test() {
        System.out.println(careerClassDao.fetchCareerClass(12));
    }


    /**
     * 初始化职业方向和具体岗位的方法
     */
    @Test
    @Transactional
    @Rollback(false)
    void init() {
        String[] src = ("1.\t计算机/网络/通信\n" +
                "技术经理/支持\n" +
                "软件/硬件工程师\n" +
                "软件/硬件测试\n" +
                "数据库开发工程师\n" +
                "数据库管理员\n" +
                "信息技术专员\n" +
                "系统管理/工程师/分析/架构师\n" +
                "网站运营\n" +
                "网站编辑\n" +
                "电脑操作/打字/录入\n" +
                "网站设计制作\n" +
                "网络管理员\n" +
                "网络工程师\n" +
                "网站推广/SEO\n" +
                "游戏设计/开发\n" +
                "ERP技术/开发应用\n" +
                "其他\n" +
                "\n" +
                "2.\t交通/运输/物流\n" +
                "物流经理/主管\n" +
                "物流专员/助理\n" +
                "供应链管理\n" +
                "海运/空运/船运/操作人员\n" +
                "调度员\n" +
                "快递员\n" +
                "单证员\n" +
                "仓库经理/主管\n" +
                "仓库管理员\n" +
                "搬运工\n" +
                "司机\n" +
                "安检员\n" +
                "其他\n" +
                "\n" +
                "3.\t美容/美发/美甲\n" +
                "美容院店长\n" +
                "美容师\n" +
                "美体师\n" +
                "美发师\n" +
                "化妆师\n" +
                "烫染师\n" +
                "洗头工\n" +
                "美甲师\n" +
                "修脚师\n" +
                "美发助理\n" +
                "美容助理\n" +
                "发型师\n" +
                "学员\n" +
                "\n" +
                "4.\t健身/保健/洗浴\n" +
                "店长\n" +
                "香薰师\n" +
                "针灸推拿\n" +
                "健身教练\n" +
                "按摩师\n" +
                "足疗师\n" +
                "营养师\n" +
                "健身顾问\n" +
                "瑜伽教练\n" +
                "SPA水疗师\n" +
                "搓澡师\n" +
                "服务生\n" +
                "学员\n" +
                "\n" +
                "5.\t农/林/牧/渔\n" +
                "农场管理员\n" +
                "种植业生产\n" +
                "林业生产\n" +
                "农艺师\n" +
                "育种师\n" +
                "畜牧兽医师\n" +
                "畜牧业生产\n" +
                "饲养员\n" +
                "饲养护理员\n" +
                "渔业养殖/捕捞/加工\n" +
                "绿植养护\n" +
                "苗木花卉养护\n" +
                "其他\n" +
                "\n" +
                "6.\t医疗/护理\n" +
                "医生/医师\n" +
                "医库主任/药剂师\n" +
                "护士/护理/特护\n" +
                "口腔医师\n" +
                "医药管理\n" +
                "麻醉师\n" +
                "心理医生\n" +
                "其他\n" +
                "\n" +
                "7.\t采购/贸易\n" +
                "贸易经理/主管\n" +
                "贸易专员\n" +
                "商务专员/助理\n" +
                "商务经理/主管\n" +
                "采购经理/主管\n" +
                "采购专员/助理\n" +
                "报关员\n" +
                "业务跟单\n" +
                "其他\n" +
                "\n" +
                "8.\t建筑/装修/装饰\n" +
                "项目经理/主管\n" +
                "建筑师/土建工程师\n" +
                "暖气水电工\n" +
                "造价师/预算师\n" +
                "电气设计\n" +
                "测绘/测量\n" +
                "智能大厦/综合布线/弱电\n" +
                "安防工程师\n" +
                "建筑制图\n" +
                "氩弧焊工\n" +
                "油漆工\n" +
                "建筑工\n" +
                "抹灰工\n" +
                "   力工\n" +
                "   钢筋工\n" +
                "   木工\n" +
                "   瓦工\n" +
                "   混凝土工\n" +
                "   砌筑工\n" +
                "   手脚架工\n" +
                "   焊接工\n" +
                "   土石方施工人员\n" +
                "   工程管理\n" +
                "家装设计师\n" +
                "工装设计师\n" +
                "水电安装工\n" +
                "\n" +
                "9.\t房产/物业\n" +
                "物业维修\n" +
                "物业管理人员\n" +
                "招商租赁\n" +
                "空调安装工\n" +
                "售楼人员\n" +
                "\n" +
                "10.\t家政/生活服务\n" +
                "保安\n" +
                "保姆/保洁\n" +
                "钟点工\n" +
                "月嫂\n" +
                "送水/送气\n" +
                "洗衣\n" +
                "主持人/司仪\n" +
                "视频后期制作\n" +
                "搬运工\n" +
                "摄影师\n" +
                "店员/门店\n" +
                "化妆师\n" +
                "化妆助理\n" +
                "摄影后期设计\n" +
                "儿童引导\n" +
                "家电维修\n" +
                "场馆经理/主管\n" +
                "滑雪指导老师\n" +
                "球馆教练\n" +
                "场所保洁员\n" +
                "场所安保员\n" +
                "服务员\n" +
                "\n" +
                "11.\t服装/纺织/皮革\n" +
                "店长\n" +
                "裁剪缝纫\n" +
                "鞋帽制作\n" +
                "服装版师\n" +
                "陈列师\n" +
                "整烫工\n" +
                "服装展示表演\n" +
                "服装/店面/纺织设计\n" +
                "销售/市场推广\n" +
                "项目经理/主管\n" +
                "生产管理\n" +
                "质量管理/验货员\n" +
                "服装/纺织/皮革跟单\n" +
                "面料辅料开发/采购\n" +
                "服装打样/制版\n" +
                "皮革护理\n" +
                "仓库管理\n" +
                "样衣工\n" +
                "裁床工\n" +
                "其他\n" +
                "\n" +
                "12.\t法律\n" +
                "律师\n" +
                "律师助理\n" +
                "法律顾问\n" +
                "法律部经理\n" +
                "产权/专利顾问\n" +
                "专业代理\n" +
                "法律文秘\n" +
                "其他\n" +
                "\n" +
                "13.\t酒店/餐饮/住宿/旅游\n" +
                "酒店经理\n" +
                "大堂经理/领班\n" +
                "前厅接待/礼仪/迎宾\n" +
                "调酒师\n" +
                "茶艺师\n" +
                "厨师/面点师\n" +
                "旅游顾问\n" +
                "导游/票务\n" +
                "地方接待\n" +
                "服务员\n" +
                "点菜员\n" +
                "切配员\n" +
                "传菜员\n" +
                "洗碗工\n" +
                "吧台\n" +
                "杂工\n" +
                "其他\n" +
                "\n" +
                "14.\t金融/保险/投资\n" +
                "证券总监/部门经理\n" +
                "证券/期货/外汇经纪人\n" +
                "信贷管理/资信评估\n" +
                "项目管理\n" +
                "外汇交易/基/国债经理人\n" +
                "投资/理财服务\n" +
                "融资总监\n" +
                "客户经理/主管\n" +
                "风险管理\n" +
                "储备经理人\n" +
                "证券分析师\n" +
                "拍卖师\n" +
                "清算人员\n" +
                "操盘手\n" +
                "保险顾问\n" +
                "保险培训师\n" +
                "其他\n" +
                "\n" +
                "15.\t零售/超市/百货/促销\n" +
                "店长/卖场经理\n" +
                "渠道经理\n" +
                "渠道专员\n" +
                "店员/营业员/导购员\n" +
                "理货员/陈列元\n" +
                "运营组长\n" +
                "收银员\n" +
                "促销员\n" +
                "防损员/内保\n" +
                "招商经理/主管\n" +
                "其他\n" +
                "\n" +
                "16.\t教育/培训\n" +
                "教学/教务管理\n" +
                "幼儿教育\n" +
                "教师/讲师/助教\n" +
                "培训顾问\n" +
                "咨询师\n" +
                "招生\n" +
                "课题研究\n" +
                "家教\n" +
                "教育产品开发\n" +
                "其他\n" +
                "\n" +
                "17.\t美术/设计/创意\n" +
                "创意指导/总监\n" +
                "美术编辑/美术设计\n" +
                "排版制作\n" +
                "产品/包装设计\n" +
                "展示/装潢设计\n" +
                "平面设计\n" +
                "网站设计\n" +
                "广告设计\n" +
                "家具/家居用品设计\n" +
                "玩具设计\n" +
                "工艺品/珠宝设计\n" +
                "店面/陈列/展览设计\n" +
                "多媒体/动画设计\n" +
                "印刷排版/制版\n" +
                "设计管理\n" +
                "工业/产品设计\n" +
                "\n" +
                "18.\t印刷/包装/出版\n" +
                "印刷主管\n" +
                "制版设计\n" +
                "印刷技术员\n" +
                "印刷领机\n" +
                "印刷后期\n" +
                "质检\n" +
                "装订工\n" +
                "包装工\n" +
                "发行人员\n" +
                "印刷排版\n" +
                "文字编辑\n" +
                "印品整饰工\n" +
                "校对员\n" +
                "\n" +
                "19.\t广告/会展\n" +
                "广告策划\n" +
                "广告制作\n" +
                "展会招商\n" +
                "展会顾问\n" +
                "广告代言\n" +
                "广告模特\n" +
                "广告总监\n" +
                "广告宣传\n" +
                "发放员\n" +
                "讲解员\n" +
                "展会策划\n" +
                "\n" +
                "20.\t宠物/宠物用品\n" +
                "宠物医院院长\n" +
                "宠物医生/助理\n" +
                "宠物护理\n" +
                "宠物店员\n" +
                "宠物店长\n" +
                "宠物美容师\n" +
                "宠物饲养员\n" +
                "宠物营养师\n" +
                "宠物护士\n" +
                "\n" +
                "21.\t翻译\n" +
                "英语\n" +
                "日语\n" +
                "韩语\n" +
                "法语\n" +
                "俄语\n" +
                "德语\n" +
                "其他\n" +
                "\n" +
                "22.\t汽车/摩托车\n" +
                "销售顾问\n" +
                "设计工程师\n" +
                "汽车修理工\n" +
                "洗车工\n" +
                "汽车陪练\n" +
                "喷漆工\n" +
                "检验员\n" +
                "钣金工\n" +
                "美容装饰工\n" +
                "模具工\n" +
                "4S店管理\n" +
                "摩托车维修\n" +
                "其他\n" +
                "\n" +
                "23.\t普工/技工\n" +
                "铣工/磨工/焊工\n" +
                "装配调剂工\n" +
                "钳工/钣工\n" +
                "电工\n" +
                "清洁工\n" +
                "电焊工/铆焊工\n" +
                "车床/磨床/铣床/冲床工\n" +
                "水工/木工/油漆工\n" +
                "维修工\n" +
                "铲车/叉车工\n" +
                "家电安装\n" +
                "质检员\n" +
                "装卸工\n" +
                "缝纫工\n" +
                "装订工\n" +
                "氩弧焊工\n" +
                "裁刀\n" +
                "组装技工\n" +
                "强电/弱点\n" +
                "亚克力\n" +
                "丝印\n" +
                "电气\n" +
                "线切割\n" +
                "大图喷绘\n" +
                "其他\n" +
                "\n" +
                "24.\t市场/销售\n" +
                "区域经理\n" +
                "渠道拓展\n" +
                "软件销售\n" +
                "电话销售\n" +
                "医药代表\n" +
                "业务员\n" +
                "销售培训讲师\n" +
                "网络销售\n" +
                "广告销售\n" +
                "市场推广\n" +
                "市场经理\n" +
                "发行经理/主管\n" +
                "其他\n" +
                "\n" +
                "25.\t会计/审计/统计\n" +
                "财务经理\n" +
                "会计\n" +
                "成本经理/主管\n" +
                "财务分析员\n" +
                "审计专员/助理\n" +
                "审计经理/主管\n" +
                "统计员\n" +
                "出纳\n" +
                "其他\n" +
                "\n" +
                "26.\t客户服务\n" +
                "保单配送\n" +
                "网店客服\n" +
                "前台\n" +
                "电话客服\n" +
                "客服专员\n" +
                "客户经理/主管\n" +
                "售前服务\n" +
                "售后服务\n" +
                "接线员\n" +
                "\n" +
                "27.\t电子/仪表仪器/机械\n" +
                "自动或工程师\n" +
                "研发工程师\n" +
                "模拟电路设计/应用工程师\n" +
                "电子元器件工程师\n" +
                "电子/电气维修\n" +
                "电子/电气工程师\n" +
                "电路工程师/技术员\n" +
                "项目经理/产品经理\n" +
                "无线电工程师\n" +
                "设备工程师（调试/安装/维护）\n" +
                "嵌入式硬件/软件工程师\n" +
                "音频/视频工程师/技术员\n" +
                "家用电器/数码产品研发\n" +
                "集成电路IC设计/应用工程师\n" +
                "激光/光电子技术\n" +
                "机电工程师\n" +
                "仪器/仪表/计量\n" +
                "电池/电源开发\n" +
                "产品工艺/规划/制程工程师\n" +
                "测试工程师\n" +
                "半导体技术\n" +
                "版图设计工程师\n" +
                "IC验证工程师\n" +
                "FAE现场应用工程师\n" +
                "其他\n" +
                "\n" +
                "28.\t策划/文案/编辑\n" +
                "编辑/撰稿人\n" +
                "文案/策划\n" +
                "校对/录入\n" +
                "网络编辑\n" +
                "刊物编辑\n" +
                "企划文案\n" +
                "资深文案\n" +
                "文案专员\n" +
                "项目策划\n" +
                "游戏策划\n" +
                "产品策划\n" +
                "品牌策划\n" +
                "网站策划\n" +
                "其他\n" +
                "\n" +
                "29.\t能源/矿产/地质勘探\n" +
                "矿物开采\n" +
                "矿物处理\n" +
                "钻井人员\n" +
                "石油/天然气开采\n" +
                "炼铁/炼钢\n" +
                "合金冶炼\n" +
                "轧制人员\n" +
                "铸造工\n" +
                "其他\n" +
                "\n" +
                "30.\t行政/后勤/人事\n" +
                "行政经理/主管\n" +
                "行政专员/助理\n" +
                "前台/接待\n" +
                "经理助理/秘书/文员\n" +
                "图书/资料/档案管理\n" +
                "后勤人员\n" +
                "人力资源经理/主管\n" +
                "培训经理/主管\n" +
                "培训专员/助理\n" +
                "猎头顾问/助理\n" +
                "\n" +
                "31.\t生物/制药/化工/医疗器械\n" +
                "医疗器械推广\n" +
                "医药研发/药品注册\n" +
                "项目经理/主管\n" +
                "药品生产/质量管理\n" +
                "生物工程/生物制药\n" +
                "招商经理/主管\n" +
                "临床研究/协调\n" +
                "生产工艺\n" +
                "炼制生产员\n" +
                "煤化工生产员\n" +
                "化学肥料生产员\n" +
                "无机/有机化工生产员\n" +
                "合成树脂/橡胶生产\n" +
                "化学纤维生产\n" +
                "合成革生产\n" +
                "精细化工生产\n" +
                "日用化学生产\n" +
                "医疗器械维修\n" +
                "\n" +
                "32.\t司机\n" +
                "大货车司机\n" +
                "长途货车司机\n" +
                "长途客运司机\n" +
                "小货车司机\n" +
                "商务车司机\n" +
                "小车司机\n" +
                "班车司机\n" +
                "罐车司机\n" +
                "铲车司机\n" +
                "挖掘机司机\n" +
                "叉车司机\n" +
                "消防车司机\n" +
                "代驾司机\n" +
                "\n" +
                "33.\t生产/加工/制造\n" +
                "生产经理/车间主任\n" +
                "质量管理\n" +
                "设备维修维护\n" +
                "设备管理\n" +
                "陶瓷/搪瓷制品生产人员\n" +
                "粮油生产加工\n" +
                "制糖和糖制品生产人员\n" +
                "乳品/冷食品加工\n" +
                "蛋糕/面点加工\n" +
                "罐头/饮料制作\n" +
                "酿酒人员\n" +
                "食品添加剂及调味品制作\n" +
                "粮油食品制作\n" +
                "屠宰加工\n" +
                "肉/蛋食品加工\n" +
                "饲料生产加工\n" +
                "制浆工\n" +
                "造纸工\n" +
                "制版工\n" +
                "装订工\n" +
                "橡胶制品生产\n" +
                "塑料制品加工").split("\n\n");
        for (int i = 0; i < src.length; i++) {
            String[] split = src[i].split("\n");
            split[0] = split[0].split("\t")[1];
            careerClassDao.insertCareerClass(split[0]);
            split = Arrays.copyOfRange(split, 1, split.length);
            careerInfoDao.insertCareerInfos(split, i + 1);
        }
    }
}
