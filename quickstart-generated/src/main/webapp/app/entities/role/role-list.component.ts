//
// Source code generated by Celerio, a Jaxio product.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Follow us on twitter: @jaxiosoft
// Need commercial support ? Contact us: info@jaxio.com
// Template pack-angular:src/main/webapp/app/entities/entity-list.component.ts.e.vm
//
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { DataTable } from 'primeng/primeng';
import { LazyLoadEvent } from 'primeng/primeng';
import { PageResponse } from "../../support/paging";
import { MessageService } from '../../service/message.service';
import { Role } from './role';
import { RoleDetailComponent } from './role-detail.component';
import { RoleService } from './role.service';


@Component({
    moduleId: module.id,
	templateUrl: 'role-list.component.html',
	selector: 'role-list',
})
export class RoleListComponent {

    @Input() header = "Roles...";

    // When 'sub' is true, it means this list is used as a one-to-many list.
    // It belongs to a parent entity, as a result the addNew operation
    // must prefill the parent entity. The prefill is not done here, instead we
    // emit an event.
    // When 'sub' is false, we display basic search criterias
    @Input() sub : boolean;
    @Output() onAddNewClicked = new EventEmitter();

    roleToDelete : Role;
    displayDeleteDialog : boolean;

    // basic search criterias (visible if not in 'sub' mode)
    example : Role = new Role();

    // list is paginated
    currentPage : PageResponse<Role> = new PageResponse<Role>(0,0,[]);


    constructor(private router:Router, private roleService : RoleService, private messageService : MessageService) { }

    /**
     * Invoked when user presses the search button.
     */
    search(dt : DataTable) {
        if (!this.sub) {
            dt.reset();
            this.loadPage({ first: 0, rows: dt.rows, sortField: dt.sortField, sortOrder: dt.sortOrder, filters: null, multiSortMeta: dt.multiSortMeta });
        }
    }

    /**
     * Invoked automatically by primeng datatable.
     */
    loadPage(event : LazyLoadEvent) {
        this.roleService.getPage(this.example, event).
            subscribe(
                pageResponse => this.currentPage = pageResponse,
                error => this.messageService.error('Could not get the results', error)
            );
    }

    onRowSelect(event : any) {
        this.router.navigate(['/role', event.data.id]);
    }

    addNew() {
        if (this.sub) {
            this.onAddNewClicked.emit("addNew");
        } else {
            this.router.navigate(['/role', 'new']);
        }
    }

    showDeleteDialog(rowData : any) {
        this.roleToDelete = <Role> rowData;
        this.displayDeleteDialog = true;
    }

    /**
     * Invoked when user presses 'Delete' in the delete dialog. Deletes the entity on
     * the server side an remove the row from the page.
     */
    delete() {
        this.roleService.delete(this.roleToDelete.id).
            subscribe(
                response => {
                    this.currentPage.remove(this.roleToDelete);
                    this.displayDeleteDialog = false;
                    this.roleToDelete = null;
                    this.messageService.info('Deleted OK', 'Angular Rocks!');
                },
                error => this.messageService.error('Could not delete!', error)
            );
    }
}