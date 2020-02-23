/*
Navicat SQL Server Data Transfer

Source Server         : sql
Source Server Version : 140000
Source Host           : :1433
Source Database       : androiddb
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 140000
File Encoding         : 65001

Date: 2020-02-23 10:54:00
*/


-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE [dbo].[course]
GO
CREATE TABLE [dbo].[course] (
[cid] int NOT NULL IDENTITY(1,1) ,
[coursename] nvarchar(MAX) NULL ,
[teacher] nvarchar(MAX) NULL ,
[classroom] nvarchar(MAX) NULL ,
[day] int NULL ,
[classstart] int NULL ,
[classend] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[course]', RESEED, 25)
GO

-- ----------------------------
-- Table structure for uc
-- ----------------------------
DROP TABLE [dbo].[uc]
GO
CREATE TABLE [dbo].[uc] (
[id] int NOT NULL IDENTITY(1,1) ,
[uid] int NULL ,
[cid] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[uc]', RESEED, 68)
GO

-- ----------------------------
-- Table structure for usr
-- ----------------------------
DROP TABLE [dbo].[usr]
GO
CREATE TABLE [dbo].[usr] (
[uid] int NOT NULL IDENTITY(1,1) ,
[no] nvarchar(MAX) NULL ,
[pw] nvarchar(MAX) NULL ,
[school] nvarchar(MAX) NULL ,
[major] nvarchar(MAX) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[usr]', RESEED, 9)
GO

-- ----------------------------
-- Indexes structure for table course
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table course
-- ----------------------------
ALTER TABLE [dbo].[course] ADD PRIMARY KEY ([cid])
GO

-- ----------------------------
-- Indexes structure for table uc
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table uc
-- ----------------------------
ALTER TABLE [dbo].[uc] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table usr
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table usr
-- ----------------------------
ALTER TABLE [dbo].[usr] ADD PRIMARY KEY ([uid])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[uc]
-- ----------------------------
ALTER TABLE [dbo].[uc] ADD FOREIGN KEY ([cid]) REFERENCES [dbo].[course] ([cid]) ON DELETE SET NULL ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[uc] ADD FOREIGN KEY ([uid]) REFERENCES [dbo].[usr] ([uid]) ON DELETE SET NULL ON UPDATE CASCADE
GO
