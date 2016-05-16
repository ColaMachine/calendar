#coding:utf-8
#db_host='120.25.154.76'
#db_port=3306
#db_user='zgroup'
#db_pwd='zgroup123#@!'
#db_name="zoo"
import MySQLdb


class SpiderPathUrl:
    """ db_host='127.0.0.1'
    db_port=3306
    db_user='colamachine'
    db_pwd='123456'
    db_name="colamachine"  """
    db_host='120.25.154.76'
    db_port=3306
    db_user='zgroup'
    db_pwd='zgroup123#@!'
    db_name="zoo"  
    conn=None
    cur=None
    """db_host='192.168.10.138'
    db_port=3306
    db_user='twifi'
    db_pwd='twifi123$'
    db_name="mytest"
    db_host='192.168.10.138'"""
    @staticmethod
    def opendb():
        try:
            SpiderPathUrl.conn=MySQLdb.connect(host=SpiderPathUrl.db_host,user=SpiderPathUrl.db_user,passwd=SpiderPathUrl.db_pwd,port=SpiderPathUrl.db_port,db = SpiderPathUrl.db_name, use_unicode=True, charset="utf8")
            #conn=MySQLdb.connect(host=db_host,user=db_user,passwd=db_pwd,port=db_port,db = db_name, use_unicode=True, charset="utf8")
            SpiderPathUrl.cur=SpiderPathUrl.conn.cursor()
            print("open database")
        except MySQLdb.Error,e:
             print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    @staticmethod
    def commitdb():
        try:
       
           
            sql= "delete from video_hot ";
            SpiderPathUrl.cur.execute(sql)
            sql= "insert into video_hot(vname,zbname,viewnum,url,type,img,vkey,vfrom) \
            select vname,zbname,viewnum,url,type,img,vkey,vfrom from video_new_temp where old!=1 and(type ='娱乐联萌' or type ='全民星秀' or type ='龙珠星秀' or type ='星秀')"
            SpiderPathUrl.cur.execute(sql)
            sql="delete from video_new "
            SpiderPathUrl.cur.execute(sql)
            sql="insert into video_new(vname,zbname,viewnum,url,type,img,vkey,vfrom) \
            select vname,zbname,viewnum,url,type,img,vkey,vfrom from video_new_temp where old!=1 "
            SpiderPathUrl.cur.execute(sql)
            SpiderPathUrl.conn.commit()
            SpiderPathUrl.cur.close()
            SpiderPathUrl.conn.close()
            print("commit database")
        except MySQLdb.Error,e:
             print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    @staticmethod
    def clearDb():
        try:
            #conn=MySQLdb.connect(host=SpiderPathUrl.db_host,user=SpiderPathUrl.db_user,passwd=SpiderPathUrl.db_pwd,port=SpiderPathUrl.db_port,db = SpiderPathUrl.db_name, use_unicode=True, charset="utf8")
            #conn=MySQLdb.connect(host=db_host,user=db_user,passwd=db_pwd,port=db_port,db = db_name, use_unicode=True, charset="utf8")
            #cur=conn.cursor()
            print "begin for"
            #sql= "delete  from video_new  "
            sql= "update video_new_temp set old =1  "
            #print sql
            SpiderPathUrl.cur.execute(sql)
            
            #print "inseart one data"        
            #print "inseart all data"
            #conn.commit()
            #cur.close()
            #conn.close()
        except MySQLdb.Error,e:
             print "Mysql Error %d: %s" % (e.args[0], e.args[1])

    @staticmethod
    def process(response,root_path,title_path,href_path,img_path,author_path,audian_path,url_prefix,vtype_path,vkey_path,vfrom,star):
        #out=open("hello"+'.txt','w')   
        #print sys.getdefaultencoding()
        #print "begin try"
        try:
            #conn=MySQLdb.connect(host=SpiderPathUrl.db_host,user=SpiderPathUrl.db_user,passwd=SpiderPathUrl.db_pwd,port=SpiderPathUrl.db_port,db = SpiderPathUrl.db_name, use_unicode=True, charset="utf8")
            #conn=MySQLdb.connect(host=db_host,user=db_user,passwd=db_pwd,port=db_port,db = db_name, use_unicode=True, charset="utf8")
            #print("root_path:"+root_path)
            #cur=conn.cursor()
            #print "begin for"
            for sel in response.xpath(root_path):
               
              
                titles = sel.xpath(title_path).extract()

                hrefs=sel.xpath(href_path).extract()
                imgs =  sel.xpath(img_path).extract()
                authors=sel.xpath(author_path).extract()
                audians=sel.xpath(audian_path).extract()
                vtypes=sel.xpath(vtype_path).extract()
                vkeys=sel.xpath(vkey_path).extract()


                #sql= "('%sd,'%d','%d','%d','%d','%d','%d')"\
                #%(len(titles),len(authors),len(audians),len(hrefs),len(vtypes),len(imgs),len(vkeys))
                #print sql
                #print vtypes
                #sql= "insert into video_new  (vname,zbname,viewnum,url,type,img,vkey) values('%s','%s','%d','%s','%s','%s','%s','%s''')"\
                #%(titles[0],authors[0],audians[0],hrefs[0],vtypes[0],imgs[0],vkeys[0],vfrom)
                #print sql

                #print("titles"+titles[0])
                if  (not titles ) or ( not authors ):
                    continue
                if len(titles)==0:
                    print "titile length :0 \n"
                    continue
                if len(hrefs)==0:
                    print "href length :0 \n"
                    continue
                url=url_prefix+hrefs[0]
                """
                if titles:
                    out.write("\t"+titles[0])
                if authors:
                    out.write("\t"+authors[0])
                if imgs:
                    out.write("\t"+imgs[0])
              
                if audians:
                    out.write("\t"+audians[0])
                if hrefs:
                    out.write("\t"+url)
                """
                title=titles[0]
                author=authors[0]
                audian=0;
                img=imgs[0]
                href=hrefs[0]
                vkey=vkeys[0]
                vtype=vtypes[0]
                if vtype.rfind("/")!=-1:
                    vtype= vtype[vtype.rfind("/")+1:]
                if(audians[0].find("万")!=-1):
                    audian=int(float(audians[0].replace("万",""))*10000)
                else:
                    #print("view num:"+audians[0])
                    audian=int(audians[0])
                #out.write("\n")

                #value=[title,author,audian,url,vtype,img,vkey,vfrom]

                #sql= "insert into video_new  (vname,zbname,viewnum,url,type,img,vkey) values('%s','%s','%d','%s','%s','%s','%s','%s''')"%(title,author,audian,url,vtype,img,vkey,vfrom)
                #print sql
                #cur.execute('insert into video_new   (vname,zbname,viewnum,url,type,img,vkey,source) values(%s,%s,%s,%s,%s,%s,%s,%s)',value)
                #SpiderPathUrl.saveData(value);
                if star=="star":
                    SpiderPathUrl.saveDataHot(title,author,audian,url,vtype,img,vkey,vfrom);
                else:
                    SpiderPathUrl.saveDataNew(title,author,audian,url,vtype,img,vkey,vfrom);
                #print "inseart one data"
               
            #print "inseart all data"
            #conn.commit()
            #cur.close()
            #conn.close()

        except MySQLdb.Error,e:
             print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    @staticmethod
    def getJsonValue(data,key):
        if(key.find(".")>0):
            arr=key.split(".")
            if len(arr)==2:
                return data[arr[0]][arr[1]]
            if len(arr)==3:
                return data[arr[0]][int(arr[1])][arr[2]]
        else:
            return data[key]
    @staticmethod
    def processJSON(data,title_path,href_path,img_path,author_path,audian_path,url_prefix,vtype_path,vkey_path,vfrom,star):
        #out=open(vtype_path+'.txt','w')   
        #print sys.getdefaultencoding()
        #print "begin try"
        try:
            #conn=MySQLdb.connect(host=SpiderPathUrl.db_host,user=SpiderPathUrl.db_user,passwd=SpiderPathUrl.db_pwd,port=SpiderPathUrl.db_port,db = SpiderPathUrl.db_name, use_unicode=True, charset="utf8")
            #conn=MySQLdb.connect(host=db_host,user=db_user,passwd=db_pwd,port=db_port,db = db_name, use_unicode=True, charset="utf8")
            #print("root_path:"+root_path)
            #cur=conn.cursor()
            #print "begin for"
            for sel in data:
                title=SpiderPathUrl.getJsonValue(sel,title_path)
              
               
                href=SpiderPathUrl.getJsonValue(sel,href_path)
                img= SpiderPathUrl.getJsonValue(sel,img_path)
                author=SpiderPathUrl.getJsonValue(sel,author_path)
                audian=SpiderPathUrl.getJsonValue(sel,audian_path)
                vtype=SpiderPathUrl.getJsonValue(sel,vtype_path)
                vkey=SpiderPathUrl.getJsonValue(sel,vkey_path)
                url=url_prefix+href
                #out.write("\t"+title)
                #out.write("\t"+author)
                #out.write("\t"+img)
                #out.write("\t"+audian)
                #out.write("\t"+url)
                if(audian.find("万")!=-1):
                    audian=int(float(audian.replace("万",""))*10000)
                else:
                    #print("view num:"+audian)
                    audian=int(audian)
                #out.write("\n")

                #value=[title,author,audian,url,vtype,img,vkey,vfrom]

                #sql= "insert into video_new  (vname,zbname,viewnum,url,type,img,vkey,source) values('%s','%s','%d','%s','%s','%s','%s','%s')"%(title,author,audian,url,vtype,img,vkey,vfrom)
                #print sql
                #cur.execute('insert into video_new   (vname,zbname,viewnum,url,type,img,vkey) values(%s,%s,%s,%s,%s,%s,%s)',value)
                #SpiderPathUrl.saveDataHot(title,author,audian,url,vtype,img,vkey,vfrom);
                if star=="star":
                    SpiderPathUrl.saveDataHot(title,author,audian,url,vtype,img,vkey,vfrom);
                else:
                    SpiderPathUrl.saveDataNew(title,author,audian,url,vtype,img,vkey,vfrom);
                
                #print "inseart one data"
               
            #print "inseart all data"
            #conn.commit()
            #cur.close()
            #conn.close()

        except MySQLdb.Error,e:
             print "Mysql Error %d: %s" % (e.args[0], e.args[1])
    @staticmethod
    def saveData(data):
        SpiderPathUrl.cur.execute('insert into video_new   (vname,zbname,viewnum,url,type,img,vkey,vfrom) values(%s,%s,%s,%s,%s,%s,%s,%s)',data)
    @staticmethod
    def saveDataHot(title,author,audian,url,vtype,img,vkey,vfrom):
        #print("cha ru yi tiao shu ju \n")
        #SpiderPathUrl.cur.execute('insert into video_new_temp  (id,vname,zbname,viewnum,url,type,img,vkey,vfrom) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)',[vfrom+vkey,title,author,audian,url,vtype,img,vkey,vfrom])
        SpiderPathUrl.cur.execute("insert into video_hot_temp   (id,vname,zbname,viewnum,url,type,img,vkey,vfrom)         values(%s,%s,%s,%s,%s,%s,%s,%s,%s)  ON DUPLICATE KEY UPDATE vname=%s,zbname=%s,viewnum=%s,url=%s,type=%s,img=%s,        vkey=%s,vfrom=%s,old=0 ",[vfrom+vkey,title,author,audian,url,vtype,img,vkey,vfrom,title,author,audian,url,vtype,img,vkey,vfrom])

    @staticmethod
    def saveDataNew(title,author,audian,url,vtype,img,vkey,vfrom):
        #print("cha ru yi tiao shu ju \n")
        #SpiderPathUrl.cur.execute('insert into video_new_temp  (id,vname,zbname,viewnum,url,type,img,vkey,vfrom) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)',[vfrom+vkey,title,author,audian,url,vtype,img,vkey,vfrom])
        SpiderPathUrl.cur.execute("insert into video_new_temp   (id,vname,zbname,viewnum,url,type,img,vkey,vfrom)         values(%s,%s,%s,%s,%s,%s,%s,%s,%s)  ON DUPLICATE KEY UPDATE vname=%s,zbname=%s,viewnum=%s,url=%s,type=%s,img=%s,        vkey=%s,vfrom=%s ,old=0",[vfrom+vkey,title,author,audian,url,vtype,img,vkey,vfrom,title,author,audian,url,vtype,img,vkey,vfrom])