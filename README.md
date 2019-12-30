项目说明
=========================
参照CodeLabs的Paging示例（官方为Kotlin实现），本项目将会使用Java完整实现网络带数据库缓存的分页列表加载。<br>
所有使用的组件都基于Jetpack，并采用Google官方推荐的唯一可信数据源app架构。<br>
项目中包含两个分支：master、paging_way，便于体现由传统实现向基于paging实现的演进过程。<br>
<br>
参考CodeLabs：http://clmirror.storage.googleapis.com/codelabs/android-paging/index.html?index=..%2F..index#0

master分支
=========================
基于传统监听Scroll滚动触发分页加载，未使用到DataSource、PagedList、BoundaryCallback实现分页

paging_way分支
=========================
基于Paged实现网络带缓存的分页列表加载

知识点
=========================
1.基于Android MVVM架构和Jitpack组件集构建Google标准应用架构<br>
2.项目结构<br>
3.ViewModel<br>
4.DataBinding<br>
5.PagedListAdapter<br>
6.PadgedList<br>
7.BoundaryCallback<br>
8.Room & DataSource<br>
9.LiveData & using transformations<br>
    1.map：关联两个LiveData，使原数据变化时，调用注入的函数，随之将返回值作为变换后的数据通知<br>
    2.switchMap：使转换后数据对象可以同时监听原数据和变换函数返回的数据<br>

其他知识点
=========================
1.TextInputLayout组件<br>
2.StaggeradGridLayoutManager（RecyclerView的瀑布流）<br>
3.RecyclerView.Adapter的局部组件刷新：notify(... , Object payload)实现刷新列表项具体某个字段数据

运行截图
=========================
![image](https://github.com/ideniden/JetpackDemo/blob/master/screenshot/paged_list.png)