using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace AndroidApp.Controllers
{
    public class ValuesController : ApiController
    {
        // GET api/values
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values  
        [HttpPost] 
        public string Post([FromBody]JObject jObj)
        {
            DBEntities db = new DBEntities();
            List<usr> uData = db.usr.ToList();
            List<course> cData = db.course.ToList();
            List<uc> ucData = db.uc.ToList();

            string type = jObj["type"].ToString();       //客户端操作类型

            if (type.Equals("login"))
            {
                string inNo = jObj["no"].ToString();
                string inPw = jObj["pw"].ToString();
                //匹配成功,返回users的id,密码错误返回-1， 账号不存在返回0
                for (int i = 0; i < uData.Count; i++)
                {
                    if (uData[i].no == inNo)
                    {
                        if (uData[i].pw == inPw)
                            return uData[i].uid.ToString();
                        else
                            return "-1";
                    }
                }
                return "0";
            }
            else if(type.Equals("register"))
            {
                string no = jObj["no"].ToString();
                string pw = jObj["pw"].ToString();
                string school = jObj["school"].ToString();
                string major = jObj["major"].ToString();

                usr uTmp = db.usr.Where(usr => usr.no == no).FirstOrDefault();
                if (uTmp == null)
                {
                    usr u = new usr() { no = no, pw = pw, school = school, major = major };
                    db.usr.Add(u);
                    if (db.SaveChanges() > 0)
                        return "1";
                    return "-1";
                }
                else
                    return "0";

               
            }
            else if (type.Equals("getCourseInfo"))
            {
                int uid = int.Parse(jObj["uid"].ToString());     //得到客户端当前登录用户的id
                List<course> cUidData = new List<course>();      //储存匹配到的当前用户的课程信息
                for (int i = 0; i < ucData.Count; i++)
                {
                    if (ucData[i].uid == uid)
                    {
                        for (int j = 0; j < cData.Count; j++)
                        {
                            if (cData[j].cid == ucData[i].cid)
                            {
                                course c = new course();      //一条课程记录
                                c.cid = cData[j].cid;
                                c.coursename = cData[j].coursename;
                                c.teacher = cData[j].teacher;
                                c.classroom = cData[j].classroom;
                                c.classstart = cData[j].classstart;
                                c.classend = cData[j].classend;
                                c.day = cData[j].day;
                                cUidData.Add(c);
                            }
                        }
                    }
                }
                return JsonConvert.SerializeObject(cUidData);
            }

            else if (type.Equals("addCourse") || type.Equals("editCourse") || type.Equals("delCourse"))
            {
                int uid = int.Parse(jObj["uid"].ToString());       //得到当前用户的id
                int cid = int.Parse(jObj["cid"].ToString());        //得到课程id,若type为edit则为修改前的id，若为del则为要删除的id, 若为add则为0（防止空指针异常）

                
                if (type.Equals("delCourse"))       //删除操作
                {
                    uc ucTmp = db.uc.Where(uc => uc.uid == uid && uc.cid == cid).FirstOrDefault();
                    db.uc.Remove(ucTmp);
                }
                else
                {
                    //得到修改后的课程
                    string cn = jObj["coursename"].ToString();
                    string tn = jObj["teacher"].ToString();
                    string cr = jObj["classroom"].ToString();
                    int cs = int.Parse(jObj["classstart"].ToString());
                    int ce = int.Parse(jObj["classend"].ToString());
                    int day = int.Parse(jObj["day"].ToString());
                    if(type.Equals("addCourse"))        //若添加的课程与已存在的课程时间段有重合，则添加失败
                    {
                        List<course> cSEList = db.course.Where(course => course.classstart >= cs && course.classend <= ce && course.day == day).ToList();
                        List<uc> ucUidList = db.uc.Where(uc => uc.uid == uid).ToList();
                        for (int i = 0; i < cSEList.Count; i++)
                        {
                            for (int j = 0; j < ucUidList.Count; j++)
                            {
                                if (cSEList[i].cid == ucUidList[j].cid)
                                    return "0";
                            }
                        }
                    }

                    course cTmp1 = db.course.Where(course => course.coursename == cn &&
                                                         course.teacher == tn &&
                                                         course.classroom == cr &&
                                                         course.classstart == cs &&
                                                         course.classend == ce &&
                                                         course.day == day
                                               ).FirstOrDefault();
                    
                    course c = new course();
                    course cTmp2 = new course();
                    if (cTmp1 == null)
                    {
                        cTmp2.coursename = cn;
                        cTmp2.teacher = tn;
                        cTmp2.classroom = cr;
                        cTmp2.classstart = cs;
                        cTmp2.classend = ce;
                        cTmp2.day = day;
                        db.course.Add(cTmp2);
                        db.SaveChanges();
                        c = cTmp2;
                    }
                    else
                        c = cTmp1;
                    
                    if (type.Equals("addCourse"))        //添加操作
                    {                   
                        uc ucTmp = new uc() { uid = uid, cid = c.cid };
                        db.uc.Add(ucTmp);

                    }
                    else if (type.Equals("editCourse"))     //编辑操作
                    {
                        uc ucUid = db.uc.Where(uc => uc.uid == uid && uc.cid == cid).FirstOrDefault();
                        ucUid.cid = c.cid;
                    }
                    cid = c.cid;        //便于返回cid
                }
                if (db.SaveChanges() > 0)
                    return cid.ToString();
                return "0";
            }
            return "0";
        }
        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
