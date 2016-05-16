#import scrapy
import MySQLdb
from scrapy.spider import Spider
#from tutorial.items import DmozItem
import json
import chardet
from gl import SpiderPathUrl
import sys
class longzhu(Spider):#scrapy.spiders.Spider
    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "longzhu"
    allowed_domains = ["longzhu.com"]
    start_urls = [
        #"http://longzhu.com/channels/lol?from=left"
        #"http://longzhu.com/channels/all?from=filive"
        "http://api.plu.cn/tga/streams?max-results=50&start-index=1&sort-by=views&filter=0&game=0&callback=_callbacks_._36bxu1",
         #"http://api.plu.cn/tga/streams?max-results=50&start-index=50&sort-by=views&filter=0&game=0&callback=_callbacks_._36bxu1",
         "http://api.plu.cn/tga/streams?max-results=18&start-index=0&sort-by=top&filter=0&game=170&callback=_callbacks_._36bxu1"
    ]

    def parse(self, response):
        """ hatype="dota2"
        vkey="longzhu"
        root_path='//div[@class="list-con"]/a'
        title_path='./h3[@class="listcard-caption"]/text()'
        href_path='.//@href'
        img_path='.//img/@src'
        author_path='.//strong/text()'
        audian_path='.//span[@class="livecard-meta-item-text"]/text()'
        url_prefix=""
        SpiderPathUrl.process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,hatype,vkey) """

        responseStr= response.body_as_unicode()
        tripResponse = responseStr.replace("_callbacks_._36bxu1(",'')
        index= tripResponse.rfind(")");
        print("zuihou ) weizhi : "+str(index)+"\n")
        tripResponse=tripResponse[0:index]

        #print(tripResponse +"\n")
        #print("tripResponse:"+tripResponse)
        resultJson= json.loads(tripResponse)  
        
        title_j="channel.name"
        href_j="channel.url"
        img_j="preview"
        author_j="channel.status"
        audian_j="viewers"
        vkey="channel.domain"
        url_prefix=""
        vType_j ="game.0.name"
        SpiderPathUrl.processJSON(resultJson["data"]["items"],title_j,href_j,img_j,author_j,audian_j,url_prefix,vType_j,vkey,"longzhu","")

      