<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="服务器名字" prop="serverName">
        <el-input
          v-model="queryParams.serverName"
          placeholder="请输入服务器名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务器ip" prop="serverIp">
        <el-input
          v-model="queryParams.serverIp"
          placeholder="请输入服务器ip"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:servers:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:servers:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:servers:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:servers:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serversList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务器id" align="center" prop="serverId" />
      <el-table-column label="服务器名字" align="center" prop="serverName" />
      <el-table-column label="服务器ip" align="center" prop="serverIp" />
      <el-table-column label="服务器cpuid" align="center" prop="serverCpuid" />
      <el-table-column label="服务器本地文件路径" align="center" prop="serverLocalPath" />
      <el-table-column label="服务器上传文件路径" align="center" prop="serverBackupPath" />
      <el-table-column label="是否开启备份" align="center" prop="serverOpenBackup" :formatter="serverOpenBackupFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:servers:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:servers:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改服务器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务器名字" prop="serverName">
          <el-input v-model="form.serverName" placeholder="请输入服务器名字" />
        </el-form-item>
        <el-form-item label="服务器ip" prop="serverIp">
          <el-input v-model="form.serverIp" placeholder="请输入服务器ip" />
        </el-form-item>
        <el-form-item label="服务器cpuid" prop="serverCpuid">
          <el-input v-model="form.serverCpuid" placeholder="请输入服务器cpuid" />
        </el-form-item>
        <el-form-item label="服务器本地文件路径" prop="serverLocalPath">
          <el-input v-model="form.serverLocalPath" placeholder="请输入服务器本地文件路径" />
        </el-form-item>
        <el-form-item label="服务器上传文件路径" prop="serverBackupPath">
          <el-input v-model="form.serverBackupPath" placeholder="请输入服务器上传文件路径" />
        </el-form-item>
        <el-form-item label="是否开启备份">
          <el-radio-group v-model="form.serverOpenBackup">
            <el-radio
              v-for="dict in serverOpenBackupOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listServers, getServers, delServers, addServers, updateServers, exportServers } from "@/api/system/servers";

export default {
  name: "Servers",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 服务器表格数据
      serversList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否开启备份字典
      serverOpenBackupOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serverName: null,
        serverIp: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_open_backup").then(response => {
      this.serverOpenBackupOptions = response.data;
    });
  },
  methods: {
    /** 查询服务器列表 */
    getList() {
      this.loading = true;
      listServers(this.queryParams).then(response => {
        this.serversList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否开启备份字典翻译
    serverOpenBackupFormat(row, column) {
      return this.selectDictLabel(this.serverOpenBackupOptions, row.serverOpenBackup);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        serverId: null,
        serverName: null,
        serverIp: null,
        serverCpuid: null,
        serverLocalPath: null,
        serverBackupPath: null,
        serverOpenBackup: 0
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.serverId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const serverId = row.serverId || this.ids
      getServers(serverId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改服务器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.serverId != null) {
            updateServers(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServers(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const serverIds = row.serverId || this.ids;
      this.$confirm('是否确认删除服务器编号为"' + serverIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delServers(serverIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有服务器数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportServers(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
<style>
  .el-dialog{
    width: 800px;
  }
</style>
