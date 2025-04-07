import request from "@/utils/request.js";

/**
 * 用这个封装一下对应后端的api，只需要写一次，调用可以无数次。don't repeat yourself!!!
 */
export const adminApi = {
    getAdmins(data) {
        return request({
            url: '/admin/query',
            method: 'post',
            data: {
                username: data.username,
                name: data.name, 
                ids: data.ids,
            }
        })
    },
    addAdmin(admin) {
        return request({
            url: '/admin',
            method: 'post',
            data: admin
        })
    },
    updateAdmin(admin) {
        return request({
            url: `/admin/${admin.id}`,
            method: 'put',
            data: admin
        })
    },
    deleteAdminById(id) {
        return request({
            url: `/admin/${id}`,
            method: 'delete',
        })
    },
}