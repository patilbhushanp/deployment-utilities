		<jsp:include page="../header/header.jsp"></jsp:include>
		<form action="/createScript" method="post" autocomplete="off" >
			<div class="row">
				<div class="col-lg-4">
					<hr/>
					<div class="list-group">
						<a href="#" class="list-group-item active">
							<h4 class="list-group-item-heading">Folder Structure</h4>
						</a>
						<a href="#" class="list-group-item">
							<h4 class="list-group-item-heading">p83008.zip</h4>
							<p class="list-group-item-text patchDataDetailLevel1"><i class="fa fa-folder-open"></i> p83008</p>
							<p class="list-group-item-text patchDataDetailLevel2"><i class="fa fa-folder-open"></i> jboss</p>
							<p class="list-group-item-text patchDataDetailLevel3"><i class="fa fa-folder-open"></i> standalone</p>
							<p class="list-group-item-text patchDataDetailLevel4"><i class="fa fa-folder-open"></i> deployment</p>
							<p class="list-group-item-text patchDataDetailLevel5"><i class="fa fa-file"></i> workbench.war</p>
							<p class="list-group-item-text patchDataDetailLevel5"><i class="fa fa-file"></i> admin.war</p>
							<p class="list-group-item-text patchDataDetailLevel2"><i class="fa fa-folder-open"></i> Oversight</p>
							<p class="list-group-item-text patchDataDetailLevel3"><i class="fa fa-folder-open"></i> db</p>
							<p class="list-group-item-text patchDataDetailLevel4"><i class="fa fa-folder-open"></i> patches</p>
							<p class="list-group-item-text patchDataDetailLevel5"><i class="fa fa-file"></i> p83008.xml</p>
							<p class="list-group-item-text patchDataDetailLevel5"><i class="fa fa-file"></i> 40789.sh</p>
							<p class="list-group-item-text patchDataDetailLevel3"><i class="fa fa-folder-open"></i> lib</p>
							<p class="list-group-item-text patchDataDetailLevel4"><i class="fa fa-file"></i> module-dao.jar</p>
							<p class="list-group-item-text patchDataDetailLevel4"><i class="fa fa-file"></i> module-util.jar</p>
							<p class="list-group-item-text patchDataDetailLevel4"><i class="fa fa-file"></i> workbench.jar</p>
						</a>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-success">Run Script</button>
					</div>
				</div>
				<div class="col-lg-7">
					<br/>
					<ul class="list-group">
						<li class="list-group-item">sudo su -l web <span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li>
						<li class="list-group-item">cd web/tmp/jbossGreaterThan6 <span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li> 
						<li class="list-group-item">mkdir p83008-work <span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li>
						<li class="list-group-item">cd p83008-work<span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li>
						<li class="list-group-item">cp -r ../pXXXXX-work/pXXXXX .<span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li>
						<li class="list-group-item">mv pXXXXX p83008<span class="badge badgeSuccess"><i class="fa fa-check"></i></span></li>
						<li class="list-group-item">cd p83008<span class="badge"><i class="fa fa-spinner"></i></span></li>
						<li class="list-group-item">mkdir Oversight</li>
						<li class="list-group-item">cd Oversight</li>
						<li class="list-group-item">mkdir lib</li>
						<li class="list-group-item">cd ..</li> 						
						<li class="list-group-item">mkdir db</li>
						<li class="list-group-item">cd db</li> 
						<li class="list-group-item">mkdir patches</li>
						<li class="list-group-item">cd ../../../..</li> 
						<li class="list-group-item">wget -n http://XXX.YYY.com/Oversight/WebUI-8.3.zip</li>
						<li class="list-group-item">wget -n http://XXX.YYY.com/Oversight/OversightBuild-8.3.zip</li>
						<li class="list-group-item">unzip -o -x WebUI-8.3.zip</li>
						<li class="list-group-item">unzip -o -x OversightBuild-8.3.zip</li>
						<li class="list-group-item">cp Oversight/webui/standalone/deployments/admin.war p83008/jboss/standalone/deployments/</li>
						<li class="list-group-item">cp Oversight/webui/standalone/deployments/workbench.war p83008/jboss/standalone/deployments/</li>
						<li class="list-group-item">cp Oversight/db/patch/p83008.xml p83008/db/patch/</li>
						<li class="list-group-item">cp Oversight/db/patch/40789.sh p83008/db/patch/</li>
						<li class="list-group-item">cp Oversight/lib/module-dao.jar p83008/lib/</li>
						<li class="list-group-item">cp Oversight/lib/module-util.jar p83008/lib/</li>
						<li class="list-group-item">cp Oversight/lib/workbench.jar p83008/lib/</li>
						<li class="list-group-item">zip -r p83008.zip p83008</li>
					</ul>
				</div>
			</div>
		</form>
	</body>
</html>
