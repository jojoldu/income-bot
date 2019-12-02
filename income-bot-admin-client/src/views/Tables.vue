<template>
    <div>
        <base-header class="pb-6 pb-8 pt-5 pt-md-8" type="gradient-success">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-active-40"
                                sub-title="350,897"
                                title="Total traffic"
                                type="gradient-red">

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-chart-pie-35"
                                sub-title="2,356"
                                title="Total traffic"
                                type="gradient-orange">

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 12.18%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-money-coins"
                                sub-title="924"
                                title="Sales"
                                type="gradient-green">

                        <template slot="footer">
                            <span class="text-danger mr-2"><i class="fa fa-arrow-down"></i> 5.72%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>

                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card class="mb-4 mb-xl-0"
                                icon="ni ni-chart-bar-32"
                                sub-title="49,65%"
                                title="Performance"
                                type="gradient-info">

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 54.8%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>
            </div>
        </base-header>

        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col">
                    <projects-table title="Light Table"></projects-table>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col">
                    <projects-table title="Dark Table" type="dark"></projects-table>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
    import ProjectsTable from './Tables/ProjectsTable'

    export default {
        name: 'tables',
        components: {
            ProjectsTable
        }
    };
</script>
<style></style>
