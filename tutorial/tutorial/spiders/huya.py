#import scrapy
import MySQLdb
from scrapy.spider import Spider

#from tutorial.items import DmozItem
import os
import sys
sys.path.append("../")
sys.path.append(os.path.abspath(os.path.dirname(__file__) + '/' + '..'))
import json
import chardet
from gl import SpiderPathUrl

import sys
class huya(Spider):#scrapy.spiders.Spider
    #print("huya class \n")
    #reload(sys)
    #sys.setdefaultencoding('utf-8')
    name = "huya"
    allowed_domains = ["huya.com"]

    start_urls = [
        "http://www.huya.com/g/1663"
    ]
   
    def parse(self, response):
        #print("huya parse \n")
        #print(response.body)
        #print(self)
        #root_path='//div[@id="live-list-content"]/ul/li'
        root_path='//div[@class="video-unit"]/ul[@class="video-list"]/li'
        title_path='.//div[@class="all_live_tit"]/a/text()'
        href_path='.//a/@href'
        img_path='.//span[@class="txt all_live_txt"]/span/img/@src'
        author_path='.//a/img/@alt'
        audian_path='.//span[@class="txt all_live_txt"]/span[@class="num"]/i/text()'
        url_prefix=""
        vtype='.//a/@eid_desc'
        vkey=href_path
        SpiderPathUrl.process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,vtype,vkey,"huya","")