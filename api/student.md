<!-- 学生
• 注册
• nju邮箱，验证（验证码或点击链接）后可登录
• 修改学生信息
• 姓名、学号等 -->



### 1.学生注册


```http
post /api/student/register
```

#### Parameters

```json
{
  "username": string ,//email
  "password": string
}
```

#### Response

```json
{
  "user": userDetail
}
```

*****************************************************************

### 2.学生帐号验证

[STUDENT]

```http
put /student/verification
```

#### Parameters

```json
{
  "verification": string
}
```

#### Response
400  
```json
{
  "msg": '验证码错误'
}
```
200  
```json
{
  "msg": '验证码成功'
}
```
*****************************************************************

### 3.学生帐号信息

[STUDENT]

```http
get /student/:stdid
```

#### Response

```json
{
  "user": userDetail
}
```

*****************************************************************

### 4.学生帐号信息修改

[STUDENT]

```http
put /student/:stdid
```

#### Parameters

```json
{
  "user": userDetail
}
```

#### Response

```json
{
  "user": userDetail
}
```

*****************************************************************

### XXXXXXXXXXXXXXXXXXXXXXXXXXXX

[TEACHER]

```http
post /sum/:reviewId/argue/:groupId
```

#### Parameters

```json
{
  "checkList": Array<checkListItem>
}
```

#### Response

```json
{
  "checkList": Array<checkListItem>
}
```

*****************************************************************

### XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

[TEACHER]

```http
post /sum/:reviewId/argue/:groupId
```

#### Parameters

```json
{
  "checkList": Array<checkListItem>
}
```

#### Response

```json
{
  "checkList": Array<checkListItem>
}
```

*****************************************************************

### XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

[TEACHER]

```http
post /sum/:reviewId/argue/:groupId
```

#### Parameters

```json
{
  "checkList": Array<checkListItem>
}
```

#### Response

```json
{
  "checkList": Array<checkListItem>
}
```