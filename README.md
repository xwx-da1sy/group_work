# group_work

## 6.6
现在已经写出来了User和NetWork的基本框架了，接下来我们要实现一些功能了。首先我们要实现用户的注册和登录功能。

## 6.7
不是老哥啊，这个ID到底是用来干啥的啊，我的哈希表用不到，但是我的User类里面还要去写这个ID啊。我既要实现用用户名查询并且提取用户，还要使用ID去查询提取用户。What can I say?

大早上，现在我重新学习了文件有关的知识，为了保证我们每次能够加载或者是保存之前的NetWork的数据，我们创建了data文件夹，并且在里面创建了一个network-data.txt的文件，这个文件用来保存整个社交网络的信息，包括当前用户、所有用户信息和好友关系。每次程序启动的时候，我们可以从这个文件里面加载整个社交网络；每次程序结束或者用户选择保存的时候，我们可以把当前社交网络重新保存到这个文件里面。

这样做的话老师就可以去查看这个文件里面的内容，看看我们是否正确的保存了用户的信息了。使用相对路径不用访问电脑上的文件。

描述的时候可以进行这样的描述:

```text
The program does not use network programming. Instead, all users are stored in one local social network file, and the current user can be switched to simulate different user perspectives.
```

这句话既可以放在presentation里面，也可以放在report里面，反正就是要说明我们这个程序没有使用网络编程，而是使用了一个本地的社交网络文件来存储所有的用户，并且可以切换当前用户来模拟不同用户的视角。逻辑自洽。

我在NetWorkFileManager里面规定了一个文件的格式，大体分成三个部分：

```text
CURRENT_USER
alice

USERS
username|password|userId|homeTown|workPlace

FRIENDSHIPS
username1|username2
```

现在问题在于二者怎么相互产生关联，信息怎么传递，怎么保存怎么读取。

我觉得“检查 + 读取”这个习惯还不错。比如读取文件的时候，先检查当前这一行是不是CURRENT_USER、USERS或者FRIENDSHIPS，确认现在处于哪一个部分，然后再按照这个部分对应的格式去读取下一行信息。

这样做的好处是逻辑比较清楚，不会把当前用户、用户资料和好友关系混在一起读。它也和我们内存里面的数据结构对应得上：USERS对应HashMap里面的User对象，FRIENDSHIPS对应User里面的HashSet好友列表。后期如果要加POSTS或者LIKES，也可以继续加新的部分，而不用推翻原来的文件格式。

选择txt文件的原因是它简单易用，适合存储结构化的数据，而且我们只需要保存用户的信息，不需要保存复杂的数据结构，所以txt文件就足够了。

当然后面我们也可能会考虑其他格式的文件，现在先不考虑这些了。

