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
class douyu(Spider):#scrapy.spiders.Spider

    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "douyu"
    allowed_domains = ["douyu.com"]
    start_urls = [
        #"http://www.douyu.com/directory/game/DOTA2"
        #"http://www.douyu.com/directory/all"
        "http://www.douyu.com/directory/all?page=1&isAjax=1",
               "http://www.douyu.com/directory/game/qmxx?page=1&isAjax=1"
    ]
   
    def parse(self, response):
    
        #print(response)
        
        #root_path='//div[@id="live-list-content"]/ul/li'
        root_path='//li'
        title_path='.//h3[@class="ellipsis"]/text()'
        href_path='.//a/@href'
        img_path='.//img/@data-original'
        author_path='.//span[@class="dy-name ellipsis fl"]/text()'
        audian_path='.//span[@class="dy-num fr"]/text()'
        url_prefix="http://www.douyu.com/"
        vtype='.//span[@class="tag ellipsis"]/text()'
        vkey='.//@data-rid'
        SpiderPathUrl.process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,vtype,vkey,"douyu","")