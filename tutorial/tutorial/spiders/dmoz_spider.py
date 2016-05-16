#import scrapy
from scrapy.spider import Spider
from tutorial.items import DmozItem
import json
import sys
class DmozSpider(Spider):#scrapy.spiders.Spider
    reload(sys)
    sys.setdefaultencoding('utf-8')
    name = "dmoz"
    allowed_domains = ["douyutv.com"]
    start_urls = [
        "http://www.douyutv.com"
    ]

    def parse(self, response):
        #filename = response.url.split("/")[-2]
        #with open(filename, 'wb') as f:
            #f.write(response.body)
       # '''
        out=open('out.txt','w') 
        print sys.getdefaultencoding()
        for sel in response.xpath('//ul/li'):
          
            #item = DmozItem()
            titles = sel.xpath('a/div[@class="txt"]/text()').extract()
            links =  sel.xpath('a/div[@class="pic"]/img/@data-original').extract()
            
            if  (not titles ) or ( not links ):
                continue
            href = sel.xpath('a/@href').extract()
            #item['title']=titles[0]
            #item['link']=links[0]  
       
            #print  type(titles[0])
            #strdata= item['title'] .decode("utf-8")+ item['link'] .decode("utf-8") 
            #out.write(titles[0].decode("gbk"))
            if titles:
                out.write("\t"+titles[0])
            
            if links:
                out.write("\t"+links[0])
            
            if href:
                out.write("\t http://www.douyutv.com"+href[0])
            
            
            out.write("\n")
            
            #out.write(titles[0]+"\t"+links[0]+"\t http://www.douyutv.com/"+href[0]);
            #out.write(titles[0]+"\t"+links[0]+"\t http://www.douyutv.com"+href)
            #out.write("\n")
            #yield  item
            
        #'''
        '''
        out=open('out.txt','w') 
        for sel in response.xpath('/html/head'):
            a=sel.xpath('title/text()').extract()
            out.write("heloo")
        '''
          
      