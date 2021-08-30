<%-- START Account status and other information--%>
<div class="card mt-3">
    <ul class="list-group list-group-flush">

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                Balance:
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.balance}</b></span>
        </li>

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                Account status:
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.status}</b></span>
        </li>

        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
            <h6 class="mb-0">
                Tariff plan:
            </h6>
            <span class="text-secondary"><b> ${sessionScope.user.tariff.description}</b></span>
        </li>
    </ul>
</div>
<%-- END Account status and other information--%>