# group_work

## 6.6
现在已经写出来了User和NetWork的基本框架了，接下来我们要实现一些功能了。首先我们要实现用户的注册和登录功能。

## 6.7

### 文件读取和存储
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
0

USERS
userId|username|password|homeTown|workPlace

FRIENDSHIPS
userId1|userId2
```

现在问题在于二者怎么相互产生关联，信息怎么传递，怎么保存怎么读取。

我觉得“检查 + 读取”这个习惯还不错。比如读取文件的时候，先检查当前这一行是不是CURRENT_USER、USERS或者FRIENDSHIPS，确认现在处于哪一个部分，然后再按照这个部分对应的格式去读取下一行信息。

这样做的好处是逻辑比较清楚，不会把当前用户、用户资料和好友关系混在一起读。它也和我们内存里面的数据结构对应得上：USERS对应HashMap里面的User对象，FRIENDSHIPS对应User里面的HashSet好友列表。后期如果要加POSTS或者LIKES，也可以继续加新的部分，而不用推翻原来的文件格式。

选择txt文件的原因是它简单易用，适合存储结构化的数据，而且我们只需要保存用户的信息，不需要保存复杂的数据结构，所以txt文件就足够了。

当然后面我们也可能会考虑其他格式的文件，现在先不考虑这些了。

现在我们又扩展了一下文件保存逻辑：一个社交网络对应一个txt文件，所有社交网络文件都放在data文件夹里面。比如如果保存名字叫test-social-network的社交网络，程序会自动生成：

```text
data/network-test-social-network.txt
```

这样我们就不只能保存一个固定的network-data.txt，而是可以保存多个不同的社交网络。读取的时候也是一样，通过社交网络名字找到对应文件，再把文件恢复成Network对象。

目前相关方法大概是：

```text
buildNetworkFilePath(networkName)
saveNetwork(network, networkName)
loadNetwork(networkName)
```

我也写了一个简单的NetworkFileManagerTester进行测试。测试内容是创建一个包含eva和frank的社交网络，把它保存成network-test-social-network.txt，然后再读取回来，检查current user、用户总数和好友数量。

现在还有一个后面可以优化的地方：因为好友关系在内存中是双向的，所以保存的时候会同时写出eva|frank和frank|eva。这个不是严重错误，因为读取的时候addEachOther可以保证关系不会乱，但是文件会有一点重复。后面可以考虑保存好友关系的时候只保存一边。

### UI

Java中这个搞这个UI差不多是这个顺序：

```text
// 1. 设置窗口基本信息
this.setTitle(...);
this.setSize(...);

// 2. 创建 panel
panel = new JPanel(new GridLayout(7, 2));

// 3. 创建组件并 add 到 panel
panel.add(...);

// 4. 把 panel add 到窗口
this.add(panel);

// 5. 最后再显示窗口
this.setVisible(true);
```

我们使用这个顺序进行UI设计，而且我们是弄好一个组件再去弄下一个组件，这样有助于我们去学习GUI，提高代码的阅读性，同时也有助于我们去调试UI，看看每个组件的效果。

我们要注意的是一定要在最后去显示窗口。

## 6.8

### 注意

现在明白了User ID的含义了，ID相当于每一个用户的身份证号码，是唯一的，不会重复的。我们在注册用户的时候，系统会自动生成一个唯一的ID，并且把它保存在User对象里面。在这个社交网络中，可以存在名字相同，家乡相同，工作地点等等相同的不同的用户，但是ID每一个用户是不相同的。

所以我们现在把Network里面的哈希表主键改成了userId：

```text
HashMap<Integer, User>
```

也就是说：

```text
key = userId
value = User对象
```

同时User里面的好友集合也从保存username改成保存userId：

```text
HashSet<Integer> friends
```

这样即使两个用户名字一样，也不会互相覆盖。添加好友、删除好友、保存好友关系的时候，底层都使用ID来处理。为了使用方便，代码里面仍然保留了一些按照名字操作的方法，但是如果这个名字对应多个用户，就会提示需要改用ID，避免误操作。

文件格式也跟着改成ID版本：

```text
CURRENT_USER
0

USERS
0|alice|alice123|Dundee|University of Dundee
1|alice|anotherPassword|London|Tech Company

FRIENDSHIPS
0|1
```

现在的设计逻辑是：ID是唯一身份，username只是用户资料里面的显示名字。username可以重复，但是userId不能重复。

登录逻辑也统一改成使用userId和password，不再使用username和password登录。原因是username现在允许重复，如果用username登录会出现歧义。username后面可以用来显示用户信息，或者查询同名用户对应的ID，但是最终登录和建立准确关系都应该使用ID。

当然或许我们也可以使用别的途径去登录，比如邮箱，手机号，社交账号等等，我们可以向用户类添加更多的属性来支持这些登录方式，来扩展程序的多样性。

我们现在整理分类了方法，可读性变好了一点.
