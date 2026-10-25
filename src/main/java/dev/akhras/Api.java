package dev.akhras;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import org.springframework.data.domain.*;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.transaction.annotation.Transactional;
@RestController @RequestMapping("/api")
public class Api{
 private final Customers customers;private final Policies policies;private final Claims claims;
 Api(Customers c,Policies p,Claims cl){customers=c;policies=p;claims=cl;}
 @GetMapping("/policies") public Page<Domain.Policy> list(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size){if(page<0||size<1||size>100)throw new IllegalArgumentException("invalid page");return policies.findAll(PageRequest.of(page,size,Sort.by("id")));}
 @PostMapping("/customers") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('AGENT','ADMIN')")public Domain.Customer customer(@Valid @RequestBody Domain.Customer c){c.id=null;return customers.save(c);}
 @PostMapping("/policies") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('AGENT','ADMIN')") public Domain.Policy policy(@Valid @RequestBody Domain.Policy p){p.id=null;p.customer=customers.findById(p.customer.id).orElseThrow();return policies.save(p);}
 @GetMapping("/policies/{id}")public Domain.Policy policy(@PathVariable Long id){return policies.findById(id).orElseThrow();}
 @DeleteMapping("/policies/{id}") @PreAuthorize("hasRole('ADMIN')") @ResponseStatus(HttpStatus.NO_CONTENT)public void delete(@PathVariable Long id){policies.deleteById(id);}
 @PostMapping("/claims") @ResponseStatus(HttpStatus.CREATED) public Domain.Claim claim(@Valid @RequestBody Domain.Claim c){c.id=null;c.status=Domain.Status.SUBMITTED;c.policy=policies.findById(c.policy.id).orElseThrow();return claims.save(c);}
 @PatchMapping("/claims/{id}/status") @PreAuthorize("hasAnyRole('ADJUSTER','ADMIN')") @Transactional
 public Domain.Claim status(@PathVariable Long id,@RequestParam Domain.Status status){var c=claims.findById(id).orElseThrow();c.status=Domain.transition(c.status,status);return c;}
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)public java.util.Map<String,String> invalid(IllegalArgumentException e){return java.util.Map.of("error",e.getMessage());}
 @ExceptionHandler(java.util.NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND)public java.util.Map<String,String> missing(){return java.util.Map.of("error","not found");}
}
