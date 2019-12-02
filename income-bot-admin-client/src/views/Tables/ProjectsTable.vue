<template>
    <div :class="type === 'dark' ? 'bg-default': ''"
         class="card shadow">
        <div :class="type === 'dark' ? 'bg-transparent': ''"
             class="card-header border-0">
            <div class="row align-items-center">
                <div class="col">
                    <h3 :class="type === 'dark' ? 'text-white': ''" class="mb-0">
                        {{title}}
                    </h3>
                </div>
                <div class="col text-right">
                    <base-button size="sm" type="primary">See all</base-button>
                </div>
            </div>
        </div>

        <div class="table-responsive">
            <base-table :class="type === 'dark' ? 'table-dark': ''"
                        :data="tableData"
                        :thead-classes="type === 'dark' ? 'thead-dark': 'thead-light'"
                        class="table align-items-center table-flush"
                        tbody-classes="list">
                <template slot="columns">
                    <th>Project</th>
                    <th>Budget</th>
                    <th>Status</th>
                    <th>Users</th>
                    <th>Completion</th>
                    <th></th>
                </template>

                <template slot-scope="{row}">
                    <th scope="row">
                        <div class="media align-items-center">
                            <a class="avatar rounded-circle mr-3" href="#">
                                <img :src="row.img" alt="Image placeholder">
                            </a>
                            <div class="media-body">
                                <span class="name mb-0 text-sm">{{row.title}}</span>
                            </div>
                        </div>
                    </th>
                    <td class="budget">
                        {{row.budget}}
                    </td>
                    <td>
                        <badge :type="row.statusType" class="badge-dot mr-4">
                            <i :class="`bg-${row.statusType}`"></i>
                            <span class="status">{{row.status}}</span>
                        </badge>
                    </td>
                    <td>
                        <div class="avatar-group">
                            <a class="avatar avatar-sm rounded-circle" data-original-title="Ryan Tompson"
                               data-toggle="tooltip" href="#">
                                <img alt="Image placeholder" src="img/theme/team-1-800x800.jpg">
                            </a>
                            <a class="avatar avatar-sm rounded-circle" data-original-title="Romina Hadid"
                               data-toggle="tooltip" href="#">
                                <img alt="Image placeholder" src="img/theme/team-2-800x800.jpg">
                            </a>
                            <a class="avatar avatar-sm rounded-circle" data-original-title="Alexander Smith"
                               data-toggle="tooltip" href="#">
                                <img alt="Image placeholder" src="img/theme/team-3-800x800.jpg">
                            </a>
                            <a class="avatar avatar-sm rounded-circle" data-original-title="Jessica Doe"
                               data-toggle="tooltip" href="#">
                                <img alt="Image placeholder" src="img/theme/team-4-800x800.jpg">
                            </a>
                        </div>
                    </td>

                    <td>
                        <div class="d-flex align-items-center">
                            <span class="completion mr-2">{{row.completion}}%</span>
                            <div>
                                <base-progress :show-percentage="false"
                                               :type="row.statusType"
                                               :value="row.completion"
                                               class="pt-0"/>
                            </div>
                        </div>
                    </td>

                    <td class="text-right">
                        <base-dropdown class="dropdown"
                                       position="right">
                            <a aria-expanded="false" aria-haspopup="true" class="btn btn-sm btn-icon-only text-light"
                               data-toggle="dropdown" role="button" slot="title">
                                <i class="fas fa-ellipsis-v"></i>
                            </a>

                            <template>
                                <a class="dropdown-item" href="#">Action</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <a class="dropdown-item" href="#">Something else here</a>
                            </template>
                        </base-dropdown>
                    </td>

                </template>

            </base-table>
        </div>

        <div :class="type === 'dark' ? 'bg-transparent': ''"
             class="card-footer d-flex justify-content-end">
            <base-pagination total="30"></base-pagination>
        </div>

    </div>
</template>
<script>
    export default {
        name: 'projects-table',
        props: {
            type: {
                type: String
            },
            title: String
        },
        data() {
            return {
                tableData: [
                    {
                        img: 'img/theme/bootstrap.jpg',
                        title: 'Argon Design System',
                        budget: '$2500 USD',
                        status: 'pending',
                        statusType: 'warning',
                        completion: 60
                    },
                    {
                        img: 'img/theme/angular.jpg',
                        title: 'Angular Now UI Kit PRO',
                        budget: '$1800 USD',
                        status: 'completed',
                        statusType: 'success',
                        completion: 100
                    },
                    {
                        img: 'img/theme/sketch.jpg',
                        title: 'Black Dashboard',
                        budget: '$3150 USD',
                        status: 'delayed',
                        statusType: 'danger',
                        completion: 72
                    },
                    {
                        img: 'img/theme/react.jpg',
                        title: 'React Material Dashboard',
                        budget: '$4400 USD',
                        status: 'on schedule',
                        statusType: 'info',
                        completion: 90
                    },
                    {
                        img: 'img/theme/vue.jpg',
                        title: 'Vue Paper UI Kit PRO',
                        budget: '$2200 USD',
                        status: 'completed',
                        statusType: 'success',
                        completion: 100
                    }
                ]
            }
        }
    }
</script>
<style>
</style>
